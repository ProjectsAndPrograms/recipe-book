let page = 0;


document.addEventListener('DOMContentLoaded', function() {
	fetchComments();
	enableCommentSave();
	document.getElementById('loadMoreBtn').addEventListener('click', fetchComments);

});

function enableCommentSave() {
	const commentForm = document.querySelector("#commentForm");

	if (!commentForm) return;
	const flashMessage = commentForm.querySelector("#flashMessage");

	function flashAlert(status, message) {
		flashMessage.classList.add(status == "success" ? "alert-success" : "alert-danger");
		flashMessage.classList.add("show");
		flashMessage.innerHTML = message;

		setTimeout(function() {
			flashMessage.classList.remove("show");
		}, 2000);
	}

	commentForm.addEventListener('submit', function(event) {
		event.preventDefault();

		const formData = new FormData(commentForm);

		const csrfTokenMeta = document.querySelector('meta[name="_csrf"]');
		const csrfHeaderMeta = document.querySelector('meta[name="_csrf_header"]');

		const csrfToken = csrfTokenMeta ? csrfTokenMeta.getAttribute('content') : null;
		const csrfHeader = csrfHeaderMeta ? csrfHeaderMeta.getAttribute('content') : null;

		const headers = csrfToken && csrfHeader ? { [csrfHeader]: csrfToken } : {};

		fetch("/recipe/comment", {
			method: 'POST',
			headers: headers,
			body: formData
		})
			.then(response => {
				if (!response.ok) throw new Error('Network error');
				return response.json();
			})
			.then(data => {

				if (data.success) {
					flashAlert("success", data.success);
				} else if (data.error) {
					flashAlert("error", data.error);
				}

				commentForm.reset();

				page = 0;
				document.getElementById('commentSection').innerHTML = "";
				fetchComments();
			})
			.catch(error => {
				console.error('Error:', error);
				flashAlert("error", "Error: " + error.message);
			});


	});
}

function fetchComments() {
	const loadMoreBtn = document.getElementById('loadMoreBtn');
	const commentSection = document.getElementById('commentSection');

	const recipeId = commentSection.getAttribute('data-recipe-id');

	const url = `/recipe/comments?recipeId=${recipeId}&page=${page}`;
	fetch(url)
		.then(response => {
			if (!response.ok) throw new Error("Network error");
			return response.json();
		})
		.then(data => {
			commentSection.insertAdjacentHTML('beforeend', data.html);

			if (data.isLastPage || !data.html.trim()) {
				loadMoreBtn.style.display = 'none';
			} else {
				page++;
			}
			console.log("comments fetched");
			enableCommentReplyButtons();
		})
		.catch(error => console.error("Error loading comments:", error));

}

function enableCommentReplyButtons() {

	const replyBtns = document.querySelectorAll(".recipe-comments .comment-box button.reply-btn");

	if (!replyBtns) return;

	console.log("reply btns");
	replyBtns.forEach(btn => {
		console.log("btn");
		const alreadyAdded = btn.getAttribute("data-listener-added") == true ? true : false;
		if (alreadyAdded) { return; }

		btn.addEventListener("click", () => {
			const container = btn.parentElement;

			if (!container.querySelector('.reply-form')) {
				const input = createReplyInput();
				container.querySelector("#reply-section").appendChild(input);
			}
		});
		btn.setAttribute("data-listener-added", true);
	});


}

function createReplyInput() {
	const container = document.createElement("div");
	container.classList.add("reply-form");
	container.style.display = "flex";
	container.style.flexDirection = "column";
	container.style.gap = "8px";
	container.style.marginTop = "8px";


	const inputContainer = document.createElement("div");
	inputContainer.style.display = 'flex';

	const imageContainer = document.createElement("div");
	imageContainer.classList.add("image-box");
	imageContainer.classList.add("border-0");

	const image = document.createElement("img");
	const commentSection = document.querySelector("#commentSection");
	const imageUrl = commentSection.getAttribute("data-auth-user-image");
	image.src = imageUrl;
	imageContainer.appendChild(image);

	const input = document.createElement("textarea");
	input.name = "reply";
	input.rows = 1;
	input.classList.add("form-control");
	input.placeholder = "Write a reply...";
	input.style.width = "100%";
	input.style.overflow = "hidden";
	input.style.resize = "none";

	input.addEventListener("input", () => {
		input.style.height = "auto";
		input.style.height = input.scrollHeight + "px";
	});

	inputContainer.appendChild(imageContainer);
	inputContainer.appendChild(input);

	const btnContainer = document.createElement("div");
	btnContainer.style.display = "flex";
	btnContainer.style.justifyContent = "flex-end";
	btnContainer.style.gap = "8px";

	const cancelBtn = document.createElement("button");
	cancelBtn.textContent = "Cancel";
	cancelBtn.style.padding = "5px 14px";
	cancelBtn.style.backgroundColor = "#ccc";
	cancelBtn.style.color = "#000";
	cancelBtn.style.border = "none";
	cancelBtn.style.borderRadius = "20px";
	cancelBtn.style.cursor = "pointer";
	cancelBtn.style.fontSize = "14px";
	cancelBtn.addEventListener("click", () => {
		container.remove();
	});

	const replyBtn = document.createElement("button");
	replyBtn.textContent = "Reply";
	replyBtn.style.padding = "5px 14px";
	replyBtn.style.backgroundColor = "#000";
	replyBtn.style.color = "#fff";
	replyBtn.style.border = "none";
	replyBtn.style.borderRadius = "20px";
	replyBtn.style.cursor = "pointer";
	replyBtn.style.fontSize = "14px";
	replyBtn.style.transition = "background-color 0.2s ease";
	replyBtn.addEventListener("mouseover", () => {
		replyBtn.style.backgroundColor = "#222";
	});
	replyBtn.addEventListener("mouseout", () => {
		replyBtn.style.backgroundColor = "#000";
	});
	replyBtn.addEventListener("click", () => {

		const commentId = replyBtn.closest(".comment-box").getAttribute("data-comment-id");
		const recipeId = commentSection.getAttribute("data-recipe-id");

		if (input.value && input.value.trim() != "") {
			console.log(input.value);

			const csrfToken = document.querySelector('meta[name="_csrf"]').content;
			const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

			if (!csrfToken | !csrfHeader) {
				console.error("error happen");
				return;
			}

			const formData = new FormData();
			formData.append("message", input.value);
			formData.append("recipeId", recipeId);

			fetch(`/recipe/comment/${commentId}`, {
				method: 'POST',
				headers: { [csrfHeader]: csrfToken },
				body: formData
			})
				.then(res => res.json())
				.then(data => {
					if (data.status === 'success') {
							container.closest(".media-body")
						    .querySelector(".replies")
						    .insertAdjacentHTML("afterbegin", data.html);

						container.remove();
					} else {
						Toastify.error(data.message);
					}
				});


		}

	});

	btnContainer.appendChild(cancelBtn);
	btnContainer.appendChild(replyBtn);

	container.appendChild(inputContainer);
	container.appendChild(btnContainer);

	return container;
}


document.addEventListener("click", function(e) {
    if (e.target.classList.contains("load-more-replies")) {
        const btn = e.target;
        const commentId = btn.getAttribute("data-comment-id");
        let page = parseInt(btn.getAttribute("data-page"));

        fetch(`/recipe/comment/replies?commentId=${commentId}&page=${page}`)
            .then(res => res.json())
            .then(data => {
                btn.insertAdjacentHTML("beforebegin", data.html);
				
                if (data.isLastPage) {
                    btn.remove();
                } else {
                    btn.setAttribute("data-page", page + 1);
                }
            });
    }
});
