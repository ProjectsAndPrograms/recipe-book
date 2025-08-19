document.addEventListener('DOMContentLoaded', function() {
	enableUsernameCheck("#username", "#updateUsernameBtn");
});

function enableUsernameCheck(usernameSelector, updateUsernameBtn) {
	const usernameInput = document.querySelector(usernameSelector);
	const updateBtn = document.querySelector(updateUsernameBtn);
	if (!usernameInput) return;

	const errorElement = document.createElement("small");
	errorElement.classList.add("text-danger");
	errorElement.style.display = "none";
	usernameInput.parentElement.appendChild(errorElement);

	let debounceTimer;

	usernameInput.addEventListener("keyup", () => {
		clearTimeout(debounceTimer);
		debounceTimer = setTimeout(() => {
			const username = usernameInput.value.trim();
			if (username === "") {
				errorElement.style.display = "none";
				
				updateBtn.disabled = true;
				return;
			}

			fetch(`/auth/register/check-username?username=${encodeURIComponent(username)}`)
				.then(res => res.json())
				.then(data => {
					if (data.status === "error") {
						errorElement.textContent = data.message;
						errorElement.style.display = "block";
						updateBtn.disabled = true;
						
					} else {
						errorElement.style.display = "none";
						updateBtn.disabled = false;
					}
				})
				.catch(() => {
					errorElement.textContent = "Unable to check username.";
					errorElement.style.display = "block";
					updateBtn.disabled = true;
				});
		}, 400);
	});
}

document.addEventListener("DOMContentLoaded", () => {
    const input = document.getElementById("keytext");
    const deleteBtn = document.getElementById("deleteUser");
    const confirmationText = document.querySelector(".no-copy-badge").innerText.trim();

    input.addEventListener("input", () => {
        deleteBtn.disabled = input.value.trim() !== confirmationText;
    });
});