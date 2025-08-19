
/*  UPCOMMINGS
    {
        message
        position
        limit
        color
        icon
        icon-color
        background
        hideProgressBar
        newestOnTop
        closeOnClick
        rtl
        draggable
        pauseOnHover
        theme
        animation
        duration
        enableCloseIcon
        pauseOnFocusLoss
        rounded
        parent
    }
*/
class Toastify {
    static #toastBox;

    static #warningIcon = 'ti-alert';
    static #successIcon = 'ti-check';
    static #errorIcon = 'ti-close';
    static #infoIcon = 'ti-info-alt';

    static #defaultDuration = 5000;

    static #init() {
        if (!this.#toastBox) {
            this.#toastBox = document.createElement('div');
            this.#toastBox.classList.add('toastify', 'toastBox');
            document.body.appendChild(this.#toastBox);
        }
    }

    static #basicToast({ message = "Welcome to Toastify", 
                         type = "info", 
                         iconClass = this.#infoIcon,
                         duration = this.#defaultDuration }) {
        
        const toast = document.createElement('div');
        toast.classList.add('toast', type);
        toast.innerHTML = `<i class="${iconClass}"></i> ${message}`;
        
        const closeBtn = document.createElement('i');
        closeBtn.classList.add('ti-close', 'closeBtn');
        
        const hideToast = () => {
            toast.classList.add('hide');
            setTimeout(() => {
                toast.classList.add('shrink');
                setTimeout(() => {
                    toast.remove();
                }, 200);
            }, 500);
        };
        
        closeBtn.addEventListener('click', hideToast);
        toast.addEventListener('click', hideToast);
       
        toast.appendChild(closeBtn);
        this.#toastBox.appendChild(toast);

        // Timer logic for hover pause
        let timeoutId;
        let timeLeft = duration;
        let startTime;
       
        function startTimer() {
            startTime = Date.now();
            timeoutId = setTimeout(hideToast, timeLeft);
        }

        function stopTimer() {
            clearTimeout(timeoutId); 
            const elapsed = Date.now() - startTime; 
            timeLeft -= elapsed;  
        }

        toast.addEventListener('mouseenter', stopTimer);  
        toast.addEventListener('mouseleave', startTimer);

        startTimer();
    }

    static error(message, duration = this.#defaultDuration) {
        this.#init(); 
        this.#basicToast({
            message,
            type: "error",
            iconClass: this.#errorIcon,
            duration
        }); 
    }

    static success(message, duration = this.#defaultDuration) {
        this.#init(); 
        this.#basicToast({
            message,
            type: "success",
            iconClass: this.#successIcon,
            duration
        }); 
    }

    static warning(message, duration = this.#defaultDuration) {
        this.#init(); 
        this.#basicToast({
            message,
            type: "warning",
            iconClass: this.#warningIcon,
            duration
        }); 
    }
    
    static info(message, duration = this.#defaultDuration) {
        this.#init(); 
        this.#basicToast({
            message,
            type: "info",
            iconClass: this.#infoIcon,
            duration
        }); 
    }
}
