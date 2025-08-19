window.onload = () => {
	enableTagSelection();
	enableIngredientManagement();
	enableNumberInputOnText();
	enableImageSelection("#recipeImageBox");
};

function enableTagSelection() {
	const tagInput = document.querySelector(".modal #tagTitle");
	const addTagBtn = document.getElementById("addTagBtn");
	const tagBoxes = document.querySelectorAll(".tag-box");

	const tags = new Set();

	document.querySelectorAll(".tag-box .tag input").forEach(input => {
		if (input.value) tags.add(input.value.trim());
	});

	function createTagElement(tagText) {
		const tagDiv = document.createElement("div");
		tagDiv.className = "tag";

		const hiddenInput = document.createElement("input");
		hiddenInput.type = "hidden";
		hiddenInput.name = "tags[]";
		hiddenInput.value = tagText;

		const removeBtn = document.createElement("span");
		removeBtn.innerHTML = "&nbsp;&times;";
		removeBtn.classList.add('remove');

		removeBtn.addEventListener("click", () => {
			tags.delete(tagText);
			document.querySelectorAll(".tag-box .tag").forEach(tag => {
				if (tag.textContent.trim().startsWith(tagText)) {
					tag.remove();
				}
			});
		});

		tagDiv.textContent = tagText;
		tagDiv.appendChild(hiddenInput);
		tagDiv.appendChild(removeBtn);
		return tagDiv;
	}


	addTagBtn.addEventListener("click", () => {

		const tagText = tagInput.value.trim();

		console.log(tagText);
		if (tagText && !tags.has(tagText)) {
			tags.add(tagText);

			tagBoxes.forEach(tagBox => {
				const tagEl = createTagElement(tagText);
				tagBox.appendChild(tagEl);
			});

			tagInput.value = "";
		}
	});

	tagInput.addEventListener("keypress", (e) => {
		if (e.key === "Enter") {
			e.preventDefault();
			addTagBtn.click();
		}
	});

	console.log('here');
	document.querySelectorAll(".tag-box .tag").forEach(tagDiv => {

		const text = tagDiv.textContent.replace('×', '').trim();
		const closeBtn = tagDiv.querySelector('span');
		if (closeBtn) {
			console.log('close btn');
			closeBtn.addEventListener("click", () => {
				console.log('added listener');
				tags.delete(text);
				tagDiv.remove();

				document.querySelectorAll(".tag-box .tag").forEach(tag => {
					if (tag.textContent.trim().startsWith(text)) {
						tag.remove();
					}
				});
			});
		}
	});
}
function enableIngredientManagement() {
	const nameInput = document.getElementById("ingredientName");
	const qtyInput = document.getElementById("ingredientQty");
	const unitInput = document.getElementById("ingredientUnit");
	const noteInput = document.getElementById("ingredientNote");
	const addBtn = document.getElementById("addIngredientBtn");
	const container = document.querySelector(".ingredients-box");

	const ingredientSet = new Set();

	const ingredients = container.querySelectorAll(".ingredient")

	if (ingredients) {
		ingredients.forEach(div => {
			const name = div.querySelector("input[name='ingredient_names[]']").value.trim();
			if (name) ingredientSet.add(name.toLowerCase());
		});
	}

	function createIngredientElement(name, qty, unit, note, unit_id) {

		if (qty) {
			if (!(/^[0-9]+$/.test(qty))) {
				note = qty + " " + unit;
				qty = null;
				unit = null;
				unit_id = null;
			}
		}
		const wrapper = document.createElement("div");
		wrapper.className = "ingredient";

		const nameDiv = document.createElement("div");
		nameDiv.className = "name";
		nameDiv.textContent = name;

		const qtyDiv = document.createElement("div");
		qtyDiv.className = "qty";
		qtyDiv.textContent = qty || note || "";

		const unitDiv = document.createElement("div");
		unitDiv.className = "unit";
		unitDiv.textContent = " " + unit;

		const nameHidden = document.createElement("input");
		nameHidden.type = "hidden";
		nameHidden.name = "ingredient_names[]";
		nameHidden.value = name;

		const qtyHidden = document.createElement("input");
		qtyHidden.type = "hidden";
		qtyHidden.name = "ingredient_quantities[]";
		qtyHidden.value = qty !== null && qty !== "" ? qty : "0";

		const unitHidden = document.createElement("input");
		unitHidden.type = "hidden";
		unitHidden.name = "ingredient_units[]";
		unitHidden.value = unit_id;

		const noteHidden = document.createElement("input");
		noteHidden.type = "hidden";
		noteHidden.name = "ingredient_notes[]";
		noteHidden.value = note;

		const removeBtn = document.createElement("span");
		removeBtn.className = "remove";
		removeBtn.innerHTML = "&times;";
		removeBtn.style.cursor = "pointer";
		removeBtn.addEventListener("click", () => {
			ingredientSet.delete(name.toLowerCase());
			wrapper.remove();
		});

		wrapper.appendChild(nameDiv);
		wrapper.appendChild(nameHidden);
		wrapper.appendChild(qtyDiv);
		wrapper.appendChild(qtyHidden);
		wrapper.appendChild(unitDiv);
		wrapper.appendChild(unitHidden);
		wrapper.appendChild(noteHidden);
		wrapper.appendChild(removeBtn);

		return wrapper;
	}

	function tryAddIngredient() {
		const name = nameInput.value.trim();
		const qty = qtyInput?.value.trim() || "";
		const unit_id = unitInput?.value.trim() || "";
		const unit = unit_id.trim() != "" ? Array.from(unitInput.options).find(opt => opt.value === unit_id)?.text : "";
		const note = noteInput?.value.trim() || "";

		if (!name) return;

		if (!((qty && unit) || note)) return;

		const nameKey = name.toLowerCase();
		if (!ingredientSet.has(nameKey)) {
			ingredientSet.add(nameKey);
			const el = createIngredientElement(name, qty, unit, note, unit_id);
			container.appendChild(el);

			nameInput.value = "";
			qtyInput.value = "";
			unitInput.value = "";
			noteInput.value = "";
		}
	}

	addBtn.addEventListener("click", tryAddIngredient);

	[nameInput, qtyInput, unitInput, noteInput].forEach(input =>
		input.addEventListener("keypress", e => {
			if (e.key === "Enter") {
				e.preventDefault();
				tryAddIngredient();
			}
		})
	);

	document.querySelectorAll(".ingredient .remove").forEach(btn => {
		btn.addEventListener("click", () => {
			const name = btn.parentElement.querySelector("input[name='ingredient_names[]']").value;
			ingredientSet.delete(name.toLowerCase());
			btn.parentElement.remove();
		});
	});
}

function enableNumberInputOnText() {
	document.querySelectorAll('input.numeric').forEach(function(input) {
		input.addEventListener("input", function() {
			this.value = this.value.replace(/[^0-9/-]/g, '');
			this.value = this.value.replace(/([/-])([/-])/g, '$2');
		});
	});
}

function enableImageSelection(recipeImageBoxSelector) {
	const recipeImageBox = document.querySelector(recipeImageBoxSelector);
	const parentForm = document.querySelector("#recipeForm");
	const imageInput = document.querySelector("#recipeImages");

	if (!recipeImageBox || !parentForm || !imageInput) return;

	// deleting from db
	const deleteBtns = recipeImageBox.querySelectorAll(".recipe-image button");
	if(deleteBtns){
		deleteBtns.forEach(btn =>{
			btn.addEventListener('click', ()=>{
				
				const csrfToken = document.querySelector('meta[name="_csrf"]').content;
				const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

				const recipeImageId = btn.getAttribute('data-recipe-image-id');
				btn.closest(".recipe-image").remove();							
				
				if(!recipeImageId | !csrfToken | !csrfHeader){
					console.error("error happen");
					return;
				}
				
				
				fetch(`/recipe-images/${recipeImageId}`, {
					method: 'DELETE',
					headers: { [csrfHeader]: csrfToken },
				})
					.then(res => res.json())
					.then(data => {
						if (data.status === 'success') {
						} else {
							Toastify.error(data.message);
						}
					});
				
			});
			
		});
	}

	// multipart file upload

	let selectedFiles = [];
	
	imageInput.addEventListener("change", () => {
		const files = Array.from(imageInput.files);
		if (!files.length) return;

		files.forEach(file => {
			selectedFiles.push(file);
			recipeImageBox.appendChild(createImageBox(file));
		});

		imageInput.value = "";
	});

	function createImageBox(file) {
		const imageBox = document.createElement("div");
		imageBox.classList.add("recipe-image");

		const img = document.createElement("img");
		img.alt = "Picked recipe Image";
		const reader = new FileReader();
		reader.onload = e => img.src = e.target.result;
		reader.readAsDataURL(file);

		const deleteBtn = document.createElement("button");
		deleteBtn.type = "button";
		deleteBtn.classList.add("recipe-image-delete-btn");
		deleteBtn.innerHTML = `<i class="ti-trash"></i>`;

		deleteBtn.addEventListener("click", () => {
			selectedFiles = selectedFiles.filter(f => f !== file);
			imageBox.remove();
		});

		imageBox.append(img, deleteBtn);
		return imageBox;
	}

	parentForm.addEventListener("submit", e => {
		const dataTransfer = new DataTransfer();
		selectedFiles.forEach(file => dataTransfer.items.add(file));
		imageInput.files = dataTransfer.files;
	});
}


