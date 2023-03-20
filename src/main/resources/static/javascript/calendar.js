 document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {

      height: '800px',
      expandRows: true,
      slotMinTime: '08:00',
      slotMaxTime: '20:00',
      headerToolbar: {
        center: 'title',
      },
      locale: "ko",
      initialView: 'dayGridMonth',
      editable: true,
      selectable: true,
      nowIndicator: true,
      dayMaxEvents: true, 
      
  
    });

   

    calendar.render();
  });