(function () {
    const versionDropdownWrapperList = document.querySelectorAll('.version-dropdown');
    versionDropdownWrapperList.forEach(versionDropdownWrapper => {
        const dropdownToggle = versionDropdownWrapper.querySelector('.version-dropdown-toggle');
        const dropdownMenu = versionDropdownWrapper.querySelector('.version-dropdown-menu');
        const setOpened = function (opened) {
            dropdownToggle.classList.toggle('opened', opened);
            dropdownMenu.classList.toggle('opened', opened);
            dropdownToggle.setAttribute('aria-expanded', String(opened));
        };
        dropdownToggle.addEventListener('click', function () {
            setOpened(!dropdownToggle.classList.contains('opened'));
        });
        versionDropdownWrapper.addEventListener('keydown', function (event) {
            if (event.key === 'Escape' && dropdownToggle.classList.contains('opened')) {
                setOpened(false);
                dropdownToggle.focus();
            }
        });
        document.addEventListener('click', function (event) {
            if (!versionDropdownWrapper.contains(event.target)) {
                setOpened(false);
            }
        });
    });
})()
