window.onload = winLoad();
function winLoad()
{
    $.ajax({
                url: "http://localhost:8080/checkLoginStatus",
                method: "POST",
                dataType: "json",
                contentType: "application/json",
                xhrFields: 
                {
                    withCredentials: true
                },
                success: function(json) 
                {
                    console.log(json.Message);  
                    window.location.href="../HTML/DataTable.html";
                },
                error: function(rs, e) 
                {
                    console.log(rs.responseText);
                    return false;
                }
            })
}

$(document).on('click', '#menu-toggle', function() 
{
    $(this).find('i').toggleClass('glyphicon glyphicon-eye-open').toggleClass("glyphicon glyphicon-eye-close");
    var input = $("#Password");
    input.attr('type') === 'password' ? input.attr('type','text') : input.attr('type','password')
});
$(document).ready( function()
{
    $('#ValidateForm').bootstrapValidator
    ({
        fields:
        {
            userName:
            {   
                validators:
                {
                    notEmpty:
                    {
                        message:'Please enter your name'
                    },
                    stringLength:
                    {
                        max: 25,
                        message:'name should be less than 25 letters'
                    },
                    regexp: 
                    {
                        regexp: /^[a-zA-Z\s]+$/,
                        message: 'username can only consist of alphabeticals and spaces'
                    }
                }
            },
            UserPassword:
            {
                validators:
                {
                    notEmpty:
                    {
                        message:'Please enter your password '
                    },
                    different: 
                    {
                        field: 'userName',
                        message: 'password cannot be the same as UserName'
                    }
                } 
            },
        },
        submitHandler: function(form) 
        {
            login();

        }
    })
});
function login()
{
    var details =
    {
        "username":$("#Name").val(),
        "password":$("#Password").val()
    }
    var request = new XMLHttpRequest();
    request.open('POST', 'http://localhost:8080/authenticate', true);
    request.setRequestHeader('Content-Type', 'application/json');
    request.setRequestHeader('Accept', 'application/json');
    request.withCredentials = true; 
    request.onreadystatechange = function () 
    {
        if (this.readyState === 4 && request.status == 200)
        {
            var result = JSON.parse(request.response);
            swal({
                    text: result.Message,
                    icon: "success",
                    closeOnClickOutside: false,
                }).then(function() 
                {
                    localStorage.setItem('username',$("#Name").val());  
                    window.location.href="../HTML/DataTable.html";
                });   
        }
        else if(request.status == 401)
        {
            var result = JSON.parse(request.response);
            swal({
                    text: result.Message,
                    icon: "error",
                    closeOnClickOutside: false,
                });
        }
    };
        request.send(JSON.stringify(details));
};