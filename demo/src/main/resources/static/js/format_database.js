function connectToDatabase(form){
    $.ajax({
        url: "/format_database/connect_to_db",
        type: 'get',
        data: $(form).serialize(),
        success: function (response) {

            let inputs = response.split(",")
            $(form).find('input').each(function (index, input){
                $(input).removeClass("invalid")
            })
            if (inputs.indexOf('ok') === -1) {
                $(inputs).each(function (index, elem) {
                    $(form).find('input[name="'+ elem.trim() +'"]').addClass("invalid")
                })

            } else {
                $(form).find('input').each(function (index, input){
                    $(input).removeClass("invalid")
                })
                $('#loading_spinner').modal('show')

                formatDatabase(form);
            }
        }
    })
}

function formatDatabase(form){
    $.ajax({
        url: "/format_database/fill_db",
        type: 'post',
        data: $(form).serialize(),
        success: function (response){
            window.location.pathname = response;
        }
    })
}