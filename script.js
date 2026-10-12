var form =
    document.getElementById("flightForm");

form.addEventListener("submit", function(event) {
	
    event.preventDefault();
    var passengerName = document.getElementById("passengerName").value;
    var flightNumber = document.getElementById("flightNumber").value;
    var source = document.getElementById("source").value;
    var destination = document.getElementById("destination").value;
    var travelDate = document.getElementById("travelDate").value;
    var seatNumber = document.getElementById("seatNumber").value;

    var data = "passengerName=" + encodeURIComponent(passengerName) +
        "&flightNumber=" + encodeURIComponent(flightNumber) +

        "&source=" + encodeURIComponent(source) +

        "&destination=" + encodeURIComponent(destination) +

        "&travelDate=" + encodeURIComponent(travelDate) +

        "&seatNumber=" + encodeURIComponent(seatNumber);

    fetch("/book", {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: data
    })

    .then(function(response) {
        return response.text();
    })

    .then(function(result) {
        document.getElementById("message")
            .innerHTML = result;
			
        form.reset();
        loadFlights();
    });
});

function loadFlights() {

    fetch("/flights")

    .then(function(response) {
        return response.text();
    })

    .then(function(data) {

        var table =
            document.getElementById("flightTable");

        table.innerHTML = "";

        var rows = data.split("\n");

        var i = 0;

        while (i < rows.length) {

            if (rows[i].length > 0) {

                var values = rows[i].split("|");

                var row =
                    "<tr>" +

                    "<td>" +
                    values[0] +
                    "</td>" +

                    "<td>" +
                    values[1] +
                    "</td>" +

                    "<td>" +
                    values[2] +
                    "</td>" +

                    "<td>" +
                    values[3] +
                    "</td>" +

                    "<td>" +
                    values[4] +
                    "</td>" +

                    "<td>" +
                    values[5] +
                    "</td>" +

                    "<td>" +
                    values[6] +
                    "</td>" +

                    "</tr>";

                table.innerHTML =
                    table.innerHTML + row;
            }

            i++;
        }
    });
}


loadFlights();