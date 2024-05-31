function load()
{
    document.getElementById("loader").setAttribute("style","visibility:hidden");
    document.getElementById("CssLoader").setAttribute("style","visibility:hidden");
}
//if(name == "" && dob=="" && std =="" && score1 == "" && score2 == "" && practical1 == "" && practical2 == ""){
//function Validate(){

/*$(document).ready( function()
{
    console.log("ho");

    $('#form').bootstrapValidator
    ({
        feedbackIcons:
        {
            valid : 'glyphicon glyphicon-ok',
            invalid : 'glyphicon glyphicon-remove',
            validating : 'glyphicon glyphicon-refresh'
        },
        fields:
        {
            Name:
            {   
                validators:
                {
                    notEmpty:
                    {
                        message:'Please enter your full name'
                    },
                    stringLength:
                    {
                        min: 5,
                        max: 25,
                        message:'Name should be greater than 5 letters and less than 25 letters'
                    },
                    regexp: 
                    {
                        regexp: /^[a-zA-Z\s]+$/,
                        message: 'The username can only consist of alphabeticals and space'
                    }
                }
            },
        dob:
        {   
            validators:
            {
                notEmpty:
                {
                    message:'Please select your dob'
                }    
            }
        },
        std:
        {   
            validators:
            {
                notEmpty:
                {
                    message:'Please select your std'
                }    
            }
        },         
        score1:
        {
            validators:
            {   
                notEmpty:
                {
                    message:'Please enter your score1'
                },
                between: 
                {
                    min: 0,
                    max: 150,
                    message: 'Score1 must be between 0 to 150'
                },
                regexp: 
                {
                    regexp: /^[0-9]+$/,
                    message: 'Score1 consists of numbers only'
                }
            }  
        },
        score2:
        {
            validators:
            {   
                notEmpty:
                {
                    message:'Please enter your score2'
                },
                between: 
                {
                    min: 0,
                    max: 150,
                    message: 'Score2 must be between 0 to 150'
                },
                regexp: 
                {
                    regexp: /^[0-9]+$/,
                    message: 'Score2 consists of numbers only'
                }
            }  
        },
        practical1:
        {
            validators:
            {   
                notEmpty:
                {
                    message:'Please enter your practical1'
                },
                between: 
                {
                    min: 0,
                    max: 50,
                    message: 'practical1 must be between 0 to 50'
                },
                regexp: 
                {
                    regexp: /^[0-9]+$/,
                    message: 'practical1 consists of numbers only'
                }
            }  
        },
        practical2:
        {
            validators:
            {   
                notEmpty:
                {
                    message:'Please enter your practical2'
                },
                between: 
                {
                    min: 0,
                    max: 50,
                    message: 'practical2 must be between 0 to 50'
                },
                regexp: 
                {
                    regexp: /^[0-9]+$/,
                    message: 'practical2 consists of numbers only'
                }
            }  
        },

    },   
    submitHandler: function(form) 
    {
            insertstudentdata();
    }
    
    })
    
}); */
//}
//}

      
function insertstudentdata()
{
    document.getElementById("loader").setAttribute("style","visibility:visible");
    document.getElementById("CssLoader").setAttribute("style","visibility:visible");
    var student_data=
    {
        "name":$("#Name").val(),
        "gender":$("input[name='Gender']:checked").val(),
        "dob": $("#dob").val(),
        "std":$("#std").val(),
        "score1":$("#score1").val(),
        "score2":$("#score2").val(),
        "practical1":$("#practical1").val(),
        "practical2":$("#practical2").val()
    }
    $(document).ready(function () 
    {
        $(document).click(function(e) 
        {
            e.stopPropagation();
            e.preventDefault();
            e.stopImmediatePropagation();
            return false;
        });
        $(document).bind('contextmenu', function(e)
        {
            e.stopPropagation();
            e.preventDefault();
            e.stopImmediatePropagation();
            return false;
        });
    });
    var request = new XMLHttpRequest();
    request.open('POST', 'http://localhost:8080/StudentsAPI/insertstudentdetails', true);
    request.setRequestHeader('Content-Type', 'application/json');
    request.setRequestHeader('Accept', 'application/json');
    setTimeout(function() {
    document.getElementById("loader").setAttribute("style","visibility:hidden");
    document.getElementById("CssLoader").setAttribute("style","visibility:hidden");
    request.onreadystatechange = function () 
    {
      if (this.readyState === 4 && request.status == 200)
      {
        $(document).unbind('click');
        $(document).unbind('contextmenu');  
        window.alert(request.response);
        window.location.href="../HTML/tableBS.html";
        console.log(JSON.stringify(request.response));
      }
      else if(request.status == 404)
      {
        window.alert(request.response);
      }
    };
    request.send(JSON.stringify(student_data));
}, 3000);

}
//}

$(function()
{
    
    $("#form").validate(
    {
        rules: 
        {
            Name: "required",
            dob:"required",
            std:"required",
            score1:
            {
                required:true,
                max:150,
                min:0,
            },
            score2:
            {
                required:true,
                max:150,
                min:0,
            },
            practical1:
            {
                required:true,
                max:50,
                min:0,
            },
            practical2:
            {
                required:true,
                max:50,
                min:0,
            },
        },
        messages: 
        {
            Name: "Please enter your name",
            dob: "Please select your dob",
            std:"Please select your class",
            score1: 
            {
                required:"Please enter your score1 ",
                max:"score1 should be less than 150",
                min:"score1 should be greater than 0",
            },
            score2: 
            {
                required:"Please enter your score2 ",
                max:"score2 should be less than 150",
                min:"score2 should be greater than 0",
            },
            practical1: 
            {
                required:"Please enter your practical1 ",
                max:"Practical1 should be less than 50",
                min:"Practical1 should be greater than 0",
            },
            practical2: 
            {
                required:"Please enter your practical2",
                max:"Practical2 should be less than 50",
                min:"Practical2 should be greater than 0",
            },
        },
        submitHandler: function(form) 
        {
            insertstudentdata();
        }
    });
});


