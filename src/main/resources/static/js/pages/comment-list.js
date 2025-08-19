let page = 0;

document.addEventListener('DOMContentLoaded', () => {
	enableViewCommentModal(".openCommentInfoModal", "#commentInfoModal");
});

function enableViewCommentModal(allBtnSelector , modalSelector) {
	const btns = document.querySelectorAll(allBtnSelector);
	const modal = document.querySelector(modalSelector);

	if (!btns.length || !modal) return;

	btns.forEach(btn => {
		btn.addEventListener("click", () => {
			console.log('clicked');
			const commentId = btn.getAttribute("data-comment-id");
			
			fetchComments(commentId);
			$(modalSelector).modal("show");
		});
	});
}


function fetchComments(commentId) {
	
	const commentSection = document.getElementById('commentSection');
	commentSection.innerHTML = `<div class="spinner-border" role="status">
				  <span class="visually-hidden">Loading...</span>
				</div>`;
	const url = `/recipe/comments/${commentId}`;
	fetch(url)
		.then(response => {
			if (!response.ok) throw new Error("Network error");
			return response.json();
		})
		.then(data => {
			commentSection.innerHTML = "";
			commentSection.insertAdjacentHTML('beforeend', data.html);

		})
		.catch(error => console.error("Error loading comments:", error));

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

