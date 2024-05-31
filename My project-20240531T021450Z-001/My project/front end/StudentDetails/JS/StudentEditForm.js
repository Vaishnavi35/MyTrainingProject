function load()
{
    var id = sessionStorage.getItem('id');
    console.log(id);
    document.getElementById("Name").value = sessionStorage.getItem('name');
    document.getElementById("dob").value = sessionStorage.getItem('dob');
    document.getElementById("std").value = sessionStorage.getItem('std');
    document.getElementById("score1").value = sessionStorage.getItem('score1');
    document.getElementById("score2").value = sessionStorage.getItem('score2');
    document.getElementById("practical1").value = sessionStorage.getItem('practical1');
    document.getElementById("practical2").value = sessionStorage.getItem('practical2');
    var Gender = sessionStorage.getItem('gender');
    if(Gender == null){
        document.getElementById("Male").checked = true;
    }else{
    document.getElementById(Gender).checked = true;
    }
    document.getElementById("loader").setAttribute("style","visibility:hidden");
    document.getElementById("overlay").setAttribute("style","visibility:hidden");
}
$(document).ready( function()
{
    $('#dob').datepicker
    ({
        todayHighlight: true,
        autoclose: true,
    }).on('changeDate', function(e) 
    {
        $('#Form').bootstrapValidator('updateStatus', 'dob', 'NOT_VALIDATED').bootstrapValidator('validateField', 'dob');
    });
    $('#Form').bootstrapValidator
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
                        max: 25,
                        message:'Name should be less than 25 letters'
                    },
                    regexp: 
                    {
                        regexp: /^[a-zA-Z\s]+$/,
                        message: 'The username can only consist of alphabeticals and spaces'
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
                },
               callback: 
                {
                    message: 'The date must be past',
                    callback: function(value) 
                    {
                        const selectedDate = moment(value);
                        const today = moment(new Date());
                        console.log(today.diff(selectedDate,'days'));
                        console.log(today)
                        console.log(selectedDate);
                        var difference = today.diff(selectedDate);
                        if ( difference <0)
                        {
                            return false;
                        }
                        return true;
                    }
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
          updatestudentdata();
    }
    });
}); 
function updatestudentdata()
{
    document.getElementById("loader").setAttribute("style","visibility:visible");
    document.getElementById("overlay").setAttribute("style","visibility:visible");
    //var token = localStorage.getItem('Token');
    var student_data=
    {
        "id":sessionStorage.getItem('id'),
        "name":$("#Name").val(),
        "gender":$("input[name='Gender']:checked").val(),
        "dob": $("#dob").val(),
        "std":$("#std").val(),
        "score1":$("#score1").val(),
        "score2":$("#score2").val(),
        "practical1":$("#practical1").val(),
        "practical2":$("#practical2").val(),
        
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
    request.open('POST', 'http://localhost:8080/StudentsAPI/updatestudentdetails', true);
    request.setRequestHeader('Content-Type', 'application/json');
    request.setRequestHeader('Accept', 'application/json');
    request.setRequestHeader('Access-Control-Allow-Origin', '*' );
    request.withCredentials = true;
    setTimeout(function() 
    {
        document.getElementById("loader").setAttribute("style","visibility:hidden");
        document.getElementById("overlay").setAttribute("style","visibility:hidden");
        request.onreadystatechange = function () 
        {
            if (this.readyState === 4 && request.status == 200)
            {
                var result =JSON.parse(request.response);
                swal({
                text:result.Message,
                icon: "success",
                closeOnClickOutside: false,
              }).then(function() {
                window.location.href="../HTML/Datatable.html";
            });
            }
            else if(request.status == 400)
            {
                var result = JSON.parse(request.response);
               swal({
                    text: result.Message,
                    icon: "warning",
                    closeOnClickOutside: false,
                  }).then(function() {
                      sessionStorage.clear();
                      localStorage.clear();
                    window.location.href="../HTML/Login.html";
                });
            }
            else if(request.status == 423)
            {
                window.location.href="../HTML/Login.html"; 
            }
        };
        request.send(JSON.stringify(student_data));
    }, 3000);
}
