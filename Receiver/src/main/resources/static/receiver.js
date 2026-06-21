(function () {
	const canvas = document.getElementById("reconstruction-canvas");
	const context = canvas.getContext("2d");

	resizeCanvas();
	window.addEventListener("resize", resizeCanvas);

	function resizeCanvas() {
		const bounds = canvas.getBoundingClientRect();
		const ratio = window.devicePixelRatio || 1;

		canvas.width = Math.floor(bounds.width * ratio);
		canvas.height = Math.floor(bounds.height * ratio);
		context.setTransform(ratio, 0, 0, ratio, 0, 0);
	}
})();
