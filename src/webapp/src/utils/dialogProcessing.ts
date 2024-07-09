export const processBeforeMovement = () => {
    try {
        const interestedControls = document.querySelectorAll(".p-dropdown-panel,.p-datepicker");
        interestedControls.forEach((control) => {
            if (control.parentElement?.tagName.toLowerCase() == 'body')
                (control as HTMLElement).style.display = 'none';
        });
    } catch { }
}
