$("#myAlertSuccess").hide();
$("#myAlertDanger").hide();
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
                        message:'Please enter your full name'
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
                        message:'Please enter your full password '
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
   //request.setRequestHeader('Access-Control-Allow-Origin', '*' );
    request.onreadystatechange = function () 
    {
        if (this.readyState === 4 && request.status == 200)
        {
            var result = JSON.parse(request.response);
           // console.log(result.Token);
            var token= result.Token;
            //var token_issuedAt = result.Token_issued_At;
            //var token_expiredAt = result.Token_expired_At;
            //localStorage.setItem('Token',token);
            //localStorage.setItem('issuedAt',token_issuedAt);
            //localStorage.setItem('expiredAt',token_expiredAt);
            $("#myAlertSuccess").show();
            $("#myAlertSuccess").html(request.response);
            setTimeout(function() 
            {
                window.location.href="../HTML/Student.html";
            }, 2000);
        }
        else if(request.status == 401)
        {
            $("#myAlertDanger").show();
            $("#myAlertDanger").html(request.response);
            $("#myAlertDanger").fadeOut(7000);
        }
    };
    request.send(JSON.stringify(details));
};