function load()
{
  document.getElementById("loader").setAttribute("style","visibility:hidden");
  document.getElementById("CssLoader").setAttribute("style","visibility:hidden");

}
function Validate()
{
    var StudentName = document.getElementById("Name").value.trim();
    var DOB = document.getElementById("dob");
    var date_pattern = /^\d{4}[./-]\d{2}[./-]\d{2}$/;
    if (!(date_pattern.test(DOB.value)))
     {
      datevaild=false;
     } 
     else
     {
      datevaild=true;
     }
    var Score1 = document.getElementById("Score1");
    var Score2 = document.getElementById("Score2");
    var Practical1 = document.getElementById("Pract1");
    var Practical2 = document.getElementById("Pract2");
    var class_name = document.getElementById("Choose");
    if (document.getElementById('Male').checked) 
    {
      gender = document.getElementById('Male').value;
    }
    else if(document.getElementById('Female').checked) 
    {
      gender = document.getElementById('Female').value;
    }
    var error = document.getElementById("NameError");
    var err1 = document.getElementById("DOBError");
    var error2 = document.getElementById("Score1Error");
    var error3 = document.getElementById("Score2Error");
    var error4 = document.getElementById("Practical1Error");
    var error5 = document.getElementById("Practical2Error");
    var error6 = document.getElementById("STDError");

    if(StudentName != "" && datevaild==true && Score1.value != "" && Score2.value != "" && Practical1.value != "" && Practical2.value != ""  && class_name.value !=0){
     error.setAttribute("style","visibility:hidden");
     err1.setAttribute("style","visibility:hidden");
     error2.setAttribute("style","visibility:hidden");
     error3.setAttribute("style","visibility:hidden");
     error4.setAttribute("style","visibility:hidden");
     error5.setAttribute("style","visibility:hidden");
     error6.setAttribute("style","visibility:hidden");

    var selectedDate = new Date(DOB.value);
    var now = new Date();
    if (selectedDate > now || Score1.value <0 ||Score1.value >150 ||Score2.value <0 || Score2.value >150||Practical1.value >50 || Practical2.value >50||Practical1.value <0 || Practical2.value <0) 
    {
      if(selectedDate > now )
      {
       err1.innerHTML = "Date must be in the past";
       err1.setAttribute("style","visibility: visible;");
      }
      if(Score1.value >150)
      {
        error2.innerHTML =  "Score1 less than 150";
        error2.setAttribute("style","visibility:visible");
      }
      else if(Score1.value <0)
      {
        error2.innerHTML = "Score1 must be greater 0";
        error2.setAttribute("style","visibility:visible");
      }
      if(Score2.value >150)
      {
        error3.innerHTML = "Score2 less than 150";
        error3.setAttribute("style","visibility:visible");
      }
      else if(Score2.value <0)
      {
        error3.innerHTML = "Score2 must be greater 0";
        error3.setAttribute("style","visibility:visible");
      }
      if(Practical1.value >50)
      {
        error4.innerHTML = "Practical1 less than 50";
        error4.setAttribute("style","visibility:visible");
      }
      else if(Practical1.value <0)
      {
        error4.innerHTML = "Practical1 must be greater 0";
        error4.setAttribute("style","visibility:visible");
      }
      if(Practical2.value >50)
      {
        error5.innerHTML = "Practical2 less than 50";
        error5.setAttribute("style","visibility:visible");
      }
      else if(Practical2.value <0)
      {
        error5.innerHTML = "Practical2 must be greater 0";
        error5.setAttribute("style","visibility:visible");
      }
    }
    else
    {
       var loader = document.getElementById("loader");
       loader.setAttribute("style","visibility:visible");
       document.getElementById("CssLoader").setAttribute("style","visibility:visible");
       var student_data=
       {
        "name":StudentName,
        "gender":gender,
        "dob": DOB.value,
        "std":class_name.value,
        "score1":Score1.value,
        "score2":Score2.value,
        "practical1":Practical1.value,
        "practical2":Practical2.value
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
          setTimeout(() => 
          {
        this.submit(student_data);
        }, 3000);
    }
  }
    else
    {
        if(StudentName== "")
        {
          error.innerHTML = "Enter your Name"  ;
          error.setAttribute("style","visibility:visible");
        }
        else
        {
          error.setAttribute("style","visibility:hidden");
        }
        if(DOB.value == "")
        {
          err1.innerHTML = "Select your DOB"  ;
          err1.setAttribute("style","visibility: visible;");
        }else
        {
          err1.setAttribute("style","visibility:hidden");
        }
        if(class_name.value == 0)
        {
          error6.innerHTML = "Select  your Class"  ;
          error6.setAttribute("style","visibility:visible");
        }
        else
        {
          error6.setAttribute("style","visibility:hidden");
        }
        if(Score1.value == "")
        {
          error2.innerHTML = "Enter your Score1"  ;
          error2.setAttribute("style","visibility:visible");
        }
        else
        {
          error2.setAttribute("style","visibility:hidden");
        }
        if(Score2.value == "")
        {
          error3.innerHTML = "Enter your Score2"  ;
          error3.setAttribute("style","visibility:visible");
       }
       else
       {
         error3.setAttribute("style","visibility:hidden");
      }
      if(Practical1.value == "")
      {
          error4.innerHTML = "Enter your Practical1"  ;
          error4.setAttribute("style","visibility:visible");
      }
      else
      {
          error4.setAttribute("style","visibility:hidden")
      }
      if(Practical2.value == "")
      {
          error5.innerHTML = "Enter your Practical2"  ;
          error5.setAttribute("style","visibility:visible");
      }
      else
      {
          error5.setAttribute("style","visibility:hidden");
      }
    }
}
function submit(student_data) 
{
    var student_data=student_data;
    var request = new XMLHttpRequest();
    request.open('POST', 'http://localhost:8080/StudentsAPI/insertstudentdetails', true);
    request.setRequestHeader('Content-Type', 'application/json');
    request.setRequestHeader('Accept', 'application/json');
    document.getElementById("loader").setAttribute("style","visibility:hidden");
    document.getElementById("CssLoader").setAttribute("style","visibility:hidden");
    request.onreadystatechange = function () 
    {
      if (this.readyState === 4 && request.status == 200)
      {
        $(document).unbind('click');
        $(document).unbind('contextmenu');
        window.alert(request.response);
        window.location.href="../HTML/Table.html";
        console.log(JSON.stringify(request.response));
      }
      else if(request.status == 404)
      {
        window.alert(request.response);
      }
    };
    request.send(JSON.stringify(student_data));
    $('#Name').val('');$('#dob').val('');$('#Score1').val('');$('#Score2').val('');
    $('#Choose').val('0');$('#Pract1').val('');$('#Pract2').val('');
}

  