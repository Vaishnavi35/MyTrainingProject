 setTimeout(function() 
 {
  var request = new XMLHttpRequest();
  var date=new Date();
  var timeZone=date.getTimezoneOffset();
  var num = Math.floor(timeZone);
  var hours = (num / 60);
  var hourDigit =  parseInt(hours); 
  var hourtoMinutes =  hourDigit * 60;
  var minutes = num - hourtoMinutes;
  var minutesPositive = Math.abs(minutes);
  console.log(num);
  console.log(hours);
  console.log(hourtoMinutes);
  console.log(minutes);
  console.log(minutesPositive);
  if(Number(num) == 0)
  {
    timeZone = "+"+hourDigit + ":" + minutesPositive;
  }
  else if(Number(num) >0)
  {
    timeZone="-"+hourDigit + ":" + minutesPositive;
  }
  else
  {
    hourDigit=hourDigit.toString();
    hourDigit=hourDigit.slice(1);
    timeZone="+"+hourDigit + ":" + minutesPositive;
  }
  var timezone=
  {
     "tz":timeZone
  }
  console.log(timeZone);
  request.open('POST', 'http://localhost:8080/StudentsAPI/getAllStudentDetails', true);
  request.setRequestHeader('Content-Type', 'application/json');
  request.setRequestHeader('Accept', 'application/json');
  
  document.getElementById("loader").setAttribute("style","visibility:hidden");
  document.getElementById("CssLoader").setAttribute("style","visibility:hidden");
  request.onreadystatechange = function () 
  {
      if (this.readyState === 4 && request.status == 200)
   {
      var studentData=JSON.parse(request.response);
      console.log(request.response);
      var row = '<tbody>'
      for(cell = 0;cell < studentData.length; cell++)
      {
        var dateFormat = studentData[cell].dob;

       /* var date = new Date(studentData[cell].dob);
        console.log( (new Date().getTimezoneOffset(date)/60));
        var  dateFormat = (moment(date).format("DD MMM, YYYY(ddd)"));*/
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
      else if(request.status == 404)
      {
        window.alert(request.response);
      }
  };
   request.send(JSON.stringify(timezone));
    //request.send();
}, 3000);
