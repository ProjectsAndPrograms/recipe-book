document.addEventListener('DOMContentLoaded', function(){
	enableProfileImageUpload();
});

function enableProfileImageUpload(){
	const fileInput = document.getElementById('image-input');
    const imageTags = document.querySelectorAll('.image-box img');

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    fileInput.addEventListener('change', async function () {
        const file = fileInput.files[0];
        if (!file) return;

        const formData = new FormData();
        formData.append('file', file);

        try {
            const response = await fetch('/settings/profile/update-image', {
                method: 'POST',
                headers: {
                    [csrfHeader]: csrfToken
                },
                body: formData
            });

            const result = await response.json();

            if (result.status === 'success') {
                imageTags.forEach(img => {
					img.src = result.imageUrl + '?t=' + new Date().getTime()
				});
            } else {
                Toastify.error(result.message || 'Failed to upload image.');
            }
        } catch (error) {
            Toastify.error('An error occurred while uploading the image.');
        }
    });
}