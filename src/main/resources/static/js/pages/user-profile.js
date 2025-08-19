
document.addEventListener('DOMContentLoaded', function() {
	enableBannerUpload("#uploadBannerInput", ".profile-banner img");
	
});



function enableBannerUpload(inputSelector, updateSelector) {
	const fileInput = document.querySelector(inputSelector);
	const images = document.querySelectorAll(updateSelector);

	const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
	const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

	if(!fileInput){
		console.error("banner upload input not available!");
		return;
	}

	fileInput.addEventListener('change', async function() {
		const file = fileInput.files[0];
		if (!file) return;

		const formData = new FormData();
		formData.append('file', file);

		try {
			const response = await fetch('/user-detail/save-banner', {
				method: 'POST',
				headers: {
					[csrfHeader]: csrfToken
				},
				body: formData
			});

			const result = await response.json();

			if (result.status === 'success') {
				images.forEach(img => {
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
