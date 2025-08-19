document.addEventListener("DOMContentLoaded", () => {
	enableUserInfo("button.user-info-opener");
});

function enableUserInfo(selector) {
	const btns = document.querySelectorAll(selector);
	const modal = document.querySelector("#userInfoModal");

	if (!btns.length || !modal) return;

	btns.forEach(btn => {
		btn.addEventListener('click', () => {
			
			modal.querySelector("#userImage").src = btn.getAttribute("data-user-image") || 'default.jpg';
			modal.querySelector("#userName").textContent = btn.getAttribute("data-user-name") || '';
			modal.querySelector("#userUsername").textContent = '@' + (btn.getAttribute("data-user-username") || '');
			modal.querySelector("#userBio").textContent = btn.getAttribute("data-user-bio") || 'No bio available';
			modal.querySelector("#userRecipes").textContent = btn.getAttribute("data-user-recipes") || '0';
			modal.querySelector("#userLikes").textContent = btn.getAttribute("data-user-likes") || '0';
			modal.querySelector("#userDislikes").textContent = btn.getAttribute("data-user-dislikes") || '0';
			modal.querySelector("#userFollowers").textContent = btn.getAttribute("data-user-followers") || '0';
			modal.querySelector("#userFollowing").textContent = btn.getAttribute("data-user-following") || '0';
			modal.querySelector("#userCreatedAt").textContent = btn.getAttribute("data-user-created-at") || '';
			modal.querySelector("#userDeletedAt").textContent = btn.getAttribute("data-user-deleted-at") || 'Active';

			$("#userInfoModal").modal("show");
		});
	});
}
