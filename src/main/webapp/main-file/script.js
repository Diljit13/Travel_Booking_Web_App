let selectedTrip = "";
let selectedPrice = 0;
let selectedTransport = "";

// Handle trip selection
function selectTrip(tripName, price) {
  selectedTrip = tripName;
  selectedPrice = price;
  alert(`You have selected: ${tripName} for $${price}`);
}

// Handle transport mode selection and review
function proceedToReview() {
  selectedTransport = document.getElementById("transportSelect").value;

  // Show the review section
  document.querySelector(".transport-selection").style.display = "none";
  document.querySelector(".review-section").style.display = "block";

  // Display the review content
  document.getElementById("review-trip").innerText = `Trip: ${selectedTrip}`;
  document.getElementById(
    "review-price"
  ).innerText = `Price: $${selectedPrice}`;
  document.getElementById(
    "review-transport"
  ).innerText = `Transport Mode: ${selectedTransport}`;
}

// Handle booking confirmation
function confirmBooking() {
  alert(
    `Booking Confirmed!\nTrip: ${selectedTrip}\nPrice: $${selectedPrice}\nTransport Mode: ${selectedTransport}`
  );
  // Redirect to a payment page or thank you page (for demo purposes)
  window.location.href = "payment.html"; // You can modify this as needed
}

// Cancel the booking process and return to the previous page
function cancelBooking() {
  alert("Booking canceled");
  document.querySelector(".review-section").style.display = "none";
  document.querySelector(".transport-selection").style.display = "block";
}
