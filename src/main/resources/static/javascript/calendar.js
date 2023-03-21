 document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {

      height: '800px',
      expandRows: true,    
      headerToolbar: {
        center: 'title',
      },
      locale: "ko",
      initialView: 'dayGridYear',
      initialDate: '2026-07-01',
      editable: true,
      selectable: true,
      nowIndicator: true,
      dayMaxEvents: true, 
      
  
    });

   

    calendar.render();
  });