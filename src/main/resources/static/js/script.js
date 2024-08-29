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

const search = () => {
    let query = $("#search-input").val();

    if (query == '') {
        $(".search-result").hide();
    }
    else {
        // sending request to server
        let url = `http://localhost:8080/search/${query}`;

        fetch(url)
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                // data

                let text = `<div class='list-group'>`;

                data.forEach(contact => {
                    text += `<a href='/user/contact/${contact.cId}' class='list-group-item list-group-item-action'> ${contact.name} </a>`;
                });

                text += `</div>`;

                $(".search-result").html(text);
                $(".search-result").show();
            });
    }
}