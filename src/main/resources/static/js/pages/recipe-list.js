document.addEventListener("DOMContentLoaded", () => {
	enableRecipeInfo("button.recipe-info");
});

function enableRecipeInfo(selector) {
	const btns = document.querySelectorAll(selector);
	if (!btns) return;

	const modal = document.querySelector("#recipeInfoModal");
	if (!modal) return;

	btns.forEach(btn => {
		btn.addEventListener('click', () => {

		modal.querySelector("#difficulty").textContent = btn.getAttribute("data-recipe-difficulty") || '';
		modal.querySelector("#servings").textContent = btn.getAttribute("data-recipe-servings") || '';
		modal.querySelector("#prepTime").textContent = btn.getAttribute("data-recipe-prep-time") || '';
		modal.querySelector("#cookTime").textContent = btn.getAttribute("data-recipe-cook-time") || '';
		modal.querySelector("#category").textContent = btn.getAttribute("data-recipe-category") || '';

		$("#recipeInfoModal").modal("show");
	});
	});
}
