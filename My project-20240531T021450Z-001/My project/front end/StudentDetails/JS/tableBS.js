document.getElementById("username").innerHTML = localStorage.getItem('username');
$(document).ready(function()
{
  $("#search").on("keyup", function() 
  {
    $("#clear").show();
    var value = $(this).val().toLowerCase();
    $("#tableData tr").filter(function() 
    {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
  $("#clear").toggle(Boolean($("#search").val()));
  $("#clear").click(function()
  {
    $("#search").val('').focus();
    $(this).hide();
    $("#tableData tr").show();
  });
});
  var request = new XMLHttpRequest();
  request.open('POST', 'http://localhost:8080/StudentsAPI/getStudentDetails', true);
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
      if(request.response === ""){
        window.location.href="../HTML/Login.html";
      }else{
      var studentData=JSON.parse(request.response);
      console.log(request.response);
      var row = '<tbody>'
      for(cell = 0;cell < studentData.length; cell++)
      {
        var date = new Date(studentData[cell].dob);
        console.log( (new Date().getTimezoneOffset(date)/60));
        var  dateFormat = (moment(date).format("DD MMM, YYYY(ddd)"));
        var score1 = studentData[cell].score1;
        var score2 = studentData[cell].score2;
        var theoryTotal = (Number(score1)+Number(score2));
        var practical1 = studentData[cell].practical1;
        var practical2 = studentData[cell].practical2;
        var practicalTotal = (Number(practical1)+Number(practical2));
        var average = (theoryTotal + practicalTotal)/2;
        var number = cell + 1;
         
        row+= '<tr>';
        row+= '<td>' + number + '</td>';
        row+= '<td>' + studentData[cell].name + '</td>';
        row+= '<td>' + studentData[cell].gender + '</td>';
        row+= '<td>' + dateFormat + '</td>';
        row+= '<td>' + studentData[cell].std + '</td>';
        row+= '<td>' + score1 + '</td>';
        row+= '<td>' + score2 + '</td>';
        row+= '<td>' + practical1 + '</td>';
        row+= '<td>' + practical2 + '</td>';
        row+= '<td>' + theoryTotal + '</td>';
        row+= '<td>' + practicalTotal + '</td>';
        row+= '<td>' + average + '</td>';
        row+= '</tr>';
      }
        row+='</tbody>';
        document.getElementById('tableData').innerHTML = row;
    }
  }
      else if(this.readyState === 4 && request.status == 400)
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
      
  };
    request.send();
}, 3000);

function logout(){
  swal({
    title: "Are you sure want to logout? ",
    text: "Click 'Yes' if you want to leave this page. ",
    icon: "warning",
    closeOnClickOutside: false,
    buttons: [
      'No',
      'Yes'
    ],
    dangerMode: true,
  }).then(function(isConfirm) {
    if (isConfirm) {
        
            $.ajax({
                url: "http://localhost:8080/logout",
                method: "POST",
                dataType: "json",
                xhrFields: {
                  withCredentials: true
                },
                success: function (json) {
                  swal({
                      text: json.Message,
                icon: "success",
                closeOnClickOutside: false,
              }).then(function() {
                sessionStorage.clear();
                localStorage.clear();
                window.location.href="../HTML/Login.html";
            });
        }
    })
    } else {
      swal({
        title:"Cancelled",
        text: "You are returned back to continue your work :)",
        icon: "success",
        closeOnClickOutside: false,
      })
    }
  });

}

