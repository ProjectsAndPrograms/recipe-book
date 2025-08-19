
document.addEventListener('DOMContentLoaded', function() {
	enableSubscription();
	enableLikeDislike("#like-dislike-btns");
});

function enableLikeDislike(selector) {
	const container = document.querySelector(selector);
	if (!container) return;

	const csrfToken = document.querySelector('meta[name="_csrf"]').content;
	const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
	const recipeId = container.getAttribute("data-recipe-id");

	const likeActive = container.querySelector(".like-toggle svg.active");
	const likeInactive = container.querySelector(".like-toggle svg.inactive");
	const dislikeActive = container.querySelector(".dislike-toggle svg.active");
	const dislikeInactive = container.querySelector(".dislike-toggle svg.inactive");

	const likeCountEl = container.querySelector("#likeCount");

	function updateReactionState(isLiked, isDislike) {
		if (isLiked === 'true') {
			likeActive.style.display = "block";
			likeInactive.style.display = "none";
			dislikeActive.style.display = "none";
			dislikeInactive.style.display = "block";
		} else {
			likeActive.style.display = "none";
			likeInactive.style.display = "block";
		}

		if (isDislike === 'true') {
			dislikeActive.style.display = "block";
			dislikeInactive.style.display = "none";
			likeActive.style.display = "none";
			likeInactive.style.display = "block";
		} else {
			dislikeActive.style.display = "none";
			dislikeInactive.style.display = "block";
		}
	}

	function saveReaction(type) {
		fetch(`/recipes/${recipeId}/react/${type}`, {
			method: 'POST',
			headers: { [csrfHeader]: csrfToken },
		})
			.then(res => res.json())
			.then(data => {
				if (data.status === 'success') {
					likeCountEl.textContent = data.likes == "0" ? "Like" : data.likes;
					updateReactionState(data.isLiked, data.isDisliked);
				} else {
					Toastify.error(data.message);
				}
			});
	}

	function activateLike() {
		saveReaction("LIKE");
	}

	function activateDislike() {
		
		saveReaction("DISLIKE");
	}

	likeActive.parentElement.addEventListener("click", activateLike);
	likeInactive.parentElement.addEventListener("click", activateLike);

	dislikeActive.parentElement.addEventListener("click", activateDislike);
	dislikeInactive.parentElement.addEventListener("click", activateDislike);
}

function enableSubscription() {
	const form = document.querySelector("form[name='mc-embedded-subscribe-form']");
	const emailInput = form.querySelector("input[name='email']");
	const submitButton = form.querySelector("button[name='subscribe']");

	form.addEventListener("submit", function(e) {
		e.preventDefault();

		const email = emailInput.value.trim();
		if (!email) {
			Toastify.warning("Please enter your email.");
			return;
		}

		submitButton.disabled = true;
		submitButton.textContent = "Subscribing...";

		const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
		const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');


		fetch("/subscribe", {
			method: "POST",
			headers: {
				"Content-Type": "application/x-www-form-urlencoded",
				"User-Agent": navigator.userAgent,
				[csrfHeader]: csrfToken
			},
			body: new URLSearchParams({ email })
		})
			.then(response => response.json())
			.then(data => {
				if (data.status == "success") {
					Toastify.success(data.message);
				} else if (data.status == "error") {
					Toastify.error(data.message);
				} else {
					Toastify.info(data.message);
				}
			})
			.catch(error => {
				console.error("Error:", error);
				Toastify.error("Something went wrong. Please try again later.");
			})
			.finally(() => {
				submitButton.disabled = false;
				submitButton.textContent = "Subscribe now";
				form.reset();
			});
	});
}

