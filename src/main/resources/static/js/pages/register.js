document.addEventListener('DOMContentLoaded', function() {
enableUsernameCheck("#username");
enableEmailCheck("#email");
});

function enableUsernameCheck(usernameSelector) {
    const usernameInput = document.querySelector(usernameSelector);
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
                return;
            }

            fetch(`/auth/register/check-username?username=${encodeURIComponent(username)}`)
                .then(res => res.json())
                .then(data => {
                    if (data.status === "error") {
                        errorElement.textContent = data.message;
                        errorElement.style.display = "block";
                    } else {
                        errorElement.style.display = "none";
                    }
                })
                .catch(() => {
                    errorElement.textContent = "Unable to check username.";
                    errorElement.style.display = "block";
                });
        }, 400);
    });
}

function enableEmailCheck(usernameSelector) {
    const emailInput = document.querySelector(usernameSelector);
    if (!emailInput) return;

    const errorElement = document.createElement("small");
    errorElement.classList.add("text-danger");
    errorElement.style.display = "none";
    emailInput.parentElement.appendChild(errorElement);

    let debounceTimer;

    emailInput.addEventListener("keyup", () => {
        clearTimeout(debounceTimer);
        debounceTimer = setTimeout(() => {
            const email = emailInput.value.trim();
            if (email === "") {
                errorElement.style.display = "none";
                return;
            }

            fetch(`/auth/register/check-email?email=${encodeURIComponent(email)}`)
                .then(res => res.json())
                .then(data => {
                    if (data.status === "error") {
                        errorElement.textContent = data.message;
                        errorElement.style.display = "block";
                    } else {
                        errorElement.style.display = "none";
                    }
                })
                .catch(() => {
                    errorElement.textContent = "Unable to check username.";
                    errorElement.style.display = "block";
                });
        }, 400);
    });
}
