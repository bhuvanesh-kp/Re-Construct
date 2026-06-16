(function () {
	const endpoint = "/api/mouse-movements";
	const flushIntervalMs = 1000;
	const sampleIntervalMs = 45;
	const maxBatchSize = 40;
	const maxRecentEvents = 12;

	const surface = document.getElementById("tracker-surface");
	const canvas = document.getElementById("trail-canvas");
	const context = canvas.getContext("2d");
	const crosshair = document.getElementById("crosshair");
	const toggleButton = document.getElementById("toggle-capture");
	const flushButton = document.getElementById("flush-events");
	const clearButton = document.getElementById("clear-events");
	const captureStatus = document.getElementById("capture-status");
	const sessionIdOutput = document.getElementById("session-id");
	const capturedCountOutput = document.getElementById("captured-count");
	const bufferedCountOutput = document.getElementById("buffered-count");
	const lastXOutput = document.getElementById("last-x");
	const lastYOutput = document.getElementById("last-y");
	const producerState = document.getElementById("producer-state");
	const eventList = document.getElementById("event-list");

	const sessionId = getSessionId();
	let capturing = false;
	let buffer = [];
	let capturedCount = 0;
	let sequence = 0;
	let lastSampledAt = 0;
	let flushTimer = window.setInterval(flushEvents, flushIntervalMs);
	let lastPoint = null;

	sessionIdOutput.textContent = sessionId;
	resizeCanvas();
	updateStatus();

	window.addEventListener("resize", resizeCanvas);
	surface.addEventListener("mousemove", captureMouseMove);
	surface.addEventListener("mouseleave", hideCrosshair);
	toggleButton.addEventListener("click", toggleCapture);
	flushButton.addEventListener("click", flushEvents);
	clearButton.addEventListener("click", clearEvents);
	window.addEventListener("pagehide", sendRemainingEvents);

	function getSessionId() {
		const existing = window.sessionStorage.getItem("mouse-observer-session-id");
		if (existing) {
			return existing;
		}

		const next = window.crypto && window.crypto.randomUUID
			? window.crypto.randomUUID()
			: "session-" + Date.now() + "-" + Math.random().toString(16).slice(2);
		window.sessionStorage.setItem("mouse-observer-session-id", next);
		return next;
	}

	function toggleCapture() {
		capturing = !capturing;
		updateStatus();
	}

	function updateStatus() {
		captureStatus.textContent = capturing ? "Capturing" : "Paused";
		captureStatus.classList.toggle("active", capturing);
		toggleButton.textContent = capturing ? "Pause capture" : "Start capture";
	}

	function captureMouseMove(event) {
		const bounds = surface.getBoundingClientRect();
		const x = Math.max(0, Math.min(bounds.width, event.clientX - bounds.left));
		const y = Math.max(0, Math.min(bounds.height, event.clientY - bounds.top));

		crosshair.style.left = x + "px";
		crosshair.style.top = y + "px";
		crosshair.classList.add("active");
		drawTrail(x, y);

		if (!capturing) {
			return;
		}

		const now = Date.now();
		if (now - lastSampledAt < sampleIntervalMs) {
			return;
		}
		lastSampledAt = now;

		const movement = {
			sessionId,
			occurredAt: now,
			sequence: ++sequence,
			x: Math.round(x),
			y: Math.round(y),
			viewportWidth: Math.round(bounds.width),
			viewportHeight: Math.round(bounds.height),
			path: window.location.pathname,
			eventType: "mousemove"
		};

		buffer.push(movement);
		capturedCount += 1;
		updateMetrics(movement);
		addRecentEvent(movement);

		if (buffer.length >= maxBatchSize) {
			flushEvents();
		}
	}

	function drawTrail(x, y) {
		context.lineCap = "round";
		context.lineJoin = "round";
		context.lineWidth = 3;
		context.strokeStyle = "rgba(91, 91, 214, 0.8)";

		if (lastPoint) {
			context.beginPath();
			context.moveTo(lastPoint.x, lastPoint.y);
			context.lineTo(x, y);
			context.stroke();
		}

		context.fillStyle = "rgba(15, 118, 110, 0.8)";
		context.beginPath();
		context.arc(x, y, 3, 0, Math.PI * 2);
		context.fill();
		lastPoint = { x, y };
	}

	function hideCrosshair() {
		crosshair.classList.remove("active");
		lastPoint = null;
	}

	function updateMetrics(event) {
		capturedCountOutput.textContent = String(capturedCount);
		bufferedCountOutput.textContent = String(buffer.length);
		lastXOutput.textContent = String(event.x);
		lastYOutput.textContent = String(event.y);
	}

	function addRecentEvent(event) {
		const item = document.createElement("li");
		item.textContent = "#" + event.sequence + " x:" + event.x + " y:" + event.y;
		eventList.prepend(item);

		while (eventList.children.length > maxRecentEvents) {
			eventList.lastElementChild.remove();
		}
	}

	async function flushEvents() {
		if (buffer.length === 0) {
			bufferedCountOutput.textContent = "0";
			return;
		}

		const events = buffer.splice(0, maxBatchSize);
		bufferedCountOutput.textContent = String(buffer.length);
		producerState.textContent = "producer: sending";

		try {
			const response = await fetch(endpoint, {
				method: "POST",
				headers: {
					"Content-Type": "application/json"
				},
				body: JSON.stringify({ events })
			});

			if (!response.ok) {
				throw new Error("HTTP " + response.status);
			}

			const result = await response.json();
			producerState.textContent = "producer: " + result.producer + " accepted " + result.captured;
		} catch (error) {
			buffer = events.concat(buffer);
			bufferedCountOutput.textContent = String(buffer.length);
			producerState.textContent = "producer: retry queued";
		}
	}

	function sendRemainingEvents() {
		window.clearInterval(flushTimer);
		flushTimer = null;

		if (buffer.length === 0 || !navigator.sendBeacon) {
			return;
		}

		const payload = new Blob([JSON.stringify({ events: buffer })], {
			type: "application/json"
		});
		navigator.sendBeacon(endpoint, payload);
		buffer = [];
	}

	function clearEvents() {
		buffer = [];
		capturedCount = 0;
		sequence = 0;
		lastPoint = null;
		context.clearRect(0, 0, canvas.width, canvas.height);
		eventList.replaceChildren();
		capturedCountOutput.textContent = "0";
		bufferedCountOutput.textContent = "0";
		lastXOutput.textContent = "-";
		lastYOutput.textContent = "-";
		producerState.textContent = "producer: waiting";
	}

	function resizeCanvas() {
		const bounds = surface.getBoundingClientRect();
		const ratio = window.devicePixelRatio || 1;
		canvas.width = Math.floor(bounds.width * ratio);
		canvas.height = Math.floor(bounds.height * ratio);
		context.setTransform(ratio, 0, 0, ratio, 0, 0);
		context.clearRect(0, 0, bounds.width, bounds.height);
		lastPoint = null;
	}
})();
