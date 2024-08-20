console.log("loaded");

const daysTag = document.querySelector(".days"),
currentDate = document.querySelector(".current-date"),
prevNextIcon = document.querySelectorAll(".icons span"),
days = document.querySelectorAll(".days");

var booked_time = document.getElementById("timesloth-time").innerHTML,
booked_date = document.getElementById("timesloth-date").innerHTML;

//get booked time to check all the checkboxes
booked_time = booked_time.replace("[", "");
booked_time = booked_time.replace("]", "");
booked_time = booked_time.replaceAll(",", "");

var book_time_array = booked_time.split(" ");

for(i = 0; i < book_time_array.length; i++){
    let hour = parseInt(book_time_array[i]) + 1;
    if(hour == 24){hour = "00"}
    let time_id;
    time_id = "#" + book_time_array[i] + "-" + hour;
    // console.log(time_id);
    $(time_id).prop("checked", true);
}

//update current date to date selected in the booking
if(booked_date.length == 7){
    booked_date = "0" + booked_date;
}

let date = new Date(),
currYear = parseInt(booked_date.substring(4,8)),
currMonth = parseInt(booked_date.substring(2,4)-1),
currDate = parseInt(booked_date.substring(0,2));
const realCurrMonth = currMonth,
realCurrYear = currYear;


// storing full name of all months in array
const months = ["January", "February", "March", "April", "May", "June", "July",
              "August", "September", "October", "November", "December"];

const renderCalendar = () => {
    let firstDayofMonth = new Date(currYear, currMonth, 1).getDay(), // getting first day of month
    lastDateofMonth = new Date(currYear, currMonth + 1, 0).getDate(), // getting last date of month
    lastDayofMonth = new Date(currYear, currMonth, lastDateofMonth).getDay(), // getting last day of month
    lastDateofLastMonth = new Date(currYear, currMonth, 0).getDate(); // getting last date of previous month
    let liTag = "";

    for (let i = firstDayofMonth; i > 0; i--) { // creating li of previous month last days
        liTag += `<li class="inactive">${lastDateofLastMonth - i + 1}</li>`;
    }

    for (let i = 1; i <= lastDateofMonth; i++) { // creating li of all days of current month
        // adding active class to li if the current day, month, and year matched
        let isToday = i === currDate && currMonth === realCurrMonth 
                     && currYear === realCurrYear ? "active" : "";
        liTag += `<li class="${isToday}">${i}</li>`;
    }

    for (let i = lastDayofMonth; i < 6; i++) { // creating li of next month first days
        liTag += `<li class="inactive">${i - lastDayofMonth + 1}</li>`
    }
    currentDate.innerText = `${months[currMonth]} ${currYear}`; // passing current mon and yr as currentDate text
    daysTag.innerHTML = liTag;
}
renderCalendar();

prevNextIcon.forEach(icon => { // getting prev and next icons
    icon.addEventListener("click", () => { // adding click event on both icons
        // if clicked icon is previous icon then decrement current month by 1 else increment it by 1
        currMonth = icon.id === "prev" ? currMonth - 1 : currMonth + 1;

        if(currMonth < 0 || currMonth > 11) { // if current month is less than 0 or greater than 11
            // creating a new date of current year & month and pass it as date value
            date = new Date(currYear, currMonth);
            currYear = date.getFullYear(); // updating current year with new date year
            currMonth = date.getMonth(); // updating current month with new date month
        } else {
            date = new Date(); // pass the current date as date value
        }
        renderCalendar(); // calling renderCalendar function
    });
});

days.forEach(day => {
    day.addEventListener("click", function(){change_selected_date(event.target)})
})

function change_selected_date(day){
        var old_selected_date = document.querySelectorAll(".active");
        [].forEach.call(old_selected_date, function(old_date) {
            old_date.classList.remove("active");
        });
        day.classList.add("active");
        getDateToInput(currentDate);
}

getDateToInput(currentDate);

function getDateToInput(monthYear){
    var date_active = document.querySelector(".active");
    // var input_check = document.getElementById("date-checked");
    var date_input =document.getElementById("date-input");
    var input_label = document.getElementById("label-date-checked");
    var date_check = "";
    var monthYearArray = monthYear.innerHTML.split(" ");

    var month_number = getMonthFromString(monthYearArray[0]);

    date_check = date_active.innerHTML + month_number + monthYearArray[1];

    if(date_check.length == 7){
        date_check = "0" + date_check;
    }

    // console.log(date_check);

    if(date_check.length == 8){
        // input_check.value = date_check;
        date_input.innerHTML = "<input type='checkbox' id='date-checked' checked='checked' name='date' value='" + date_check + "'></input>";
        input_label.innerHTML = date_check;
    }
}

function getMonthFromString(mon){
    const monthsLong = {
        January: '01',
        February: '02',
        March: '03',
        April: '04',
        May: '05',
        June: '06',
        July: '07',
        August: '08',
        September: '09',
        October: '10',
        November: '11',
        December: '12',
    };
    return monthsLong[mon];
}

$('#time-buttons-container').on('click', '[type=checkbox]', function (e) {
    var $el = $(e.currentTarget),
        $checkboxes = $el.closest('div').find('[type=checkbox]'),
        $checkedEls = $checkboxes.filter(':checked').filter(function (index, el) {
            return el !== $el[0];
        }),
        elIndex = $checkboxes.index($el),
        firstCheckedIndex = $checkboxes.index($checkedEls.first()),
        lastCheckedIndex = $checkboxes.index($checkedEls.last());

    return !$checkedEls.length
        || (elIndex === firstCheckedIndex - 1 || elIndex === lastCheckedIndex + 1);
});