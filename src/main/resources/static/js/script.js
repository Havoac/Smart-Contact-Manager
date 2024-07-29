const toggleSidebar = () => {
    if ($(".sidebar").is(":visible")) {
        // if the sidebar is visible, then we need to close it
        $(".sidebar").css("display", "none");
        $(".content").css("margin-left", "0%");
    }
    else {
        // if the sidebar is not visible, then we need to make it visible
        $(".sidebar").css("display", "block");
        $(".content").css("margin-left", "22%");
    }
};