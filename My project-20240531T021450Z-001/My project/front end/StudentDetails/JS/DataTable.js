document.getElementById("username").innerHTML = localStorage.getItem('username');
$.fn.dataTable.ext.errMode = 'none';
$(document).ready(function() 
{  
    $.fn.dataTable.ext.search.push(
    function(settings, data, dataIndex) 
    {
      var minDate = $('#rangeFrom').datepicker("getDate");
      var maxDate = $('#rangeTo').datepicker("getDate");
      var dobColumnDates = new Date(data[2]);
      if (minDate == null && maxDate == null) 
      {
        return true;
      }
      if (minDate == null && dobColumnDates <= maxDate) 
      {
        return true;
      }
      if (maxDate == null && dobColumnDates >= minDate) 
      {
        return true;
      }
      if (dobColumnDates <= maxDate && dobColumnDates >= minDate) 
      {
        return true;
      }
      return false;
    }
  );
  $('.form-control').datepicker
    ({
      onSelect: function() 
      {
        table.draw();
      },
          todayHighlight: true,
          autoclose: true,
          endDate: '+0d',
    })
    var table = $('.table').DataTable( 
    {   
        "paging": true,
        "pagingType": "simple_numbers",
        "responsive": true,
        "processing": true,
        "responsive": true,
        "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
        "ajax" : 
        {
            type : "POST",
            url : "http://localhost:8080/StudentsAPI/getStudentDetails",
            contentType: "application/json",
            xhrFields: {
              withCredentials: true
            },
            "dataSrc": "",
            error: function (xhr, desc, err){
              if (xhr.responseJSON && xhr.responseJSON.Message) 
              {
                swal({
                  text: xhr.responseJSON.Message,
                  icon: "warning",
                  closeOnClickOutside: false,
                }).then(function() 
                {
                  sessionStorage.clear();
                  localStorage.clear();
                  window.location.href="../HTML/Login.html";
                });
              }else{
                      window.location.href="../HTML/Login.html";
                    }
            },
        },
        columns: [
                      { data: 'name' },
                      { data: 'gender' },
                      { data: 'dob',
                          "render":function (value) 
                          {
                              if (value === null) return "";
                              return moment(value).format("DD MMM, YYYY(ddd)");
                          }
                      },
                      { data: 'std' },
                      { data:null, 
                          "render":  function (row) 
                          {
                            return parseInt(row.score1) +parseInt(row.score2) + parseInt(row.practical1) +parseInt(row.practical2);  
                          } 
                      },
                      {
                        data:null,
                        defaultContent: '<center><button type="button" class="btn btn-primary btn-sm" id = "edit" > Edit <span class="glyphicon glyphicon-pencil"></span></button></center>',
                        
                      }
                  ],    
                  "columnDefs":[
                                  {
                                      "searchable": true,
                                      "orderable": true,
                                      "targets": 2,
                                      "type": 'date'
                                  }
                                ],
    });
    $('#rangeFrom, #rangeTo').change(function() 
    {
      table.order( [2,'asc'] ).draw(); 
      table.draw();
    });
    $('body').on('click', '#edit', function()
    {
      var current_row = $(this).parents('tr');
      if (current_row.hasClass('child')) 
      {
          current_row = current_row.prev();
      }
      var details = table.row(current_row).data();
      sessionStorage.setItem('id',details.id );
      sessionStorage.setItem('name',details.name );
      sessionStorage.setItem('gender',details.gender );
      sessionStorage.setItem('dob',details.dob );
      sessionStorage.setItem('std',details.std );
      sessionStorage.setItem('score1',details.score1 );
      sessionStorage.setItem('score2',details.score2 );
      sessionStorage.setItem('practical1',details.practical1 );
      sessionStorage.setItem('practical2',details.practical2 );
      window.location.href = "../HTML/StudentEditForm.html"
    });
})

function logout()
{
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
      }).then(function(isConfirm) 
      {
          if (isConfirm) 
          {
            $.ajax({
                      url: "http://localhost:8080/logout",
                      method: "POST",
                      dataType: "json",
                      xhrFields: 
                      {
                        withCredentials: true
                      },
                      success: function (json) 
                      {
                        swal({
                                text: json.Message,
                                icon: "success",
                                closeOnClickOutside: false,
                              }).then(function() 
                              {
                                  sessionStorage.clear();
                                  localStorage.clear();
                                  window.location.href="../HTML/Login.html";
                              });
                      }
                    })
          } else 
          {
              swal({
                        title:"Cancelled",
                        text: "You are returned back to continue your work :)",
                        icon: "success",
                        closeOnClickOutside: false,
                    })
          }
    });
}