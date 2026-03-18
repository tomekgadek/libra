import { toast } from "react-toastify";

/*const toastConfig = {
    position: toast.POSITION.TOP_RIGHT,
    autoClose: 1500,
    hideProgressBar: false,
    cloaseOnClick: true,
    pauseOnHover: true,
    draggable: true,
    progress: undefined,
    theme: "light"
};*/

export function toastInfo(message) {
    toast.info(message);
}

export function toastSuccess(message) {
    toast.success(message);
}

export function toastWarning(message) {
    toast.warn(message);
}

export function toastError(message) {
    toast.error(message);
}