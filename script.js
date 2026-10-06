let map = null;
let currentLat = null;
let currentLng = null;
let routingControl = null;

function startApp(){

  document.getElementById('onboarding')
    .classList.add('hidden');

  document.getElementById('app')
    .classList.remove('hidden');

  loadMap();

}
function loginUser() {

   const name = document.getElementById("loginName").value;
   const contact = document.getElementById("contact1").value;

   if(name === "" || contact === ""){
      alert("Please fill all details");
      return;
   }

   localStorage.setItem("rr_name", name);
   localStorage.setItem("rr_contact", contact);

   document.getElementById("loginPage").style.display = "none";

   document.getElementById("app").classList.remove("hidden");

}
/* MAP */

function loadMap(){

  map = L.map('map').setView([23.2599,77.4126],13);

  L.tileLayer(
    'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
    {
      attribution:'© OpenStreetMap contributors'
    }
  ).addTo(map);

  navigator.geolocation.getCurrentPosition((position)=>{

    currentLat = position.coords.latitude;
    currentLng = position.coords.longitude;

    map.setView([currentLat,currentLng],15);

    L.marker([currentLat,currentLng])
      .addTo(map)
      .bindPopup('You are here 📍')
      .openPopup();

    const safeSpots = [
      [currentLat + 0.01,currentLng + 0.01],
      [currentLat - 0.01,currentLng + 0.02],
      [currentLat + 0.02,currentLng - 0.01]
    ];
    

    safeSpots.forEach((spot)=>{

      L.circleMarker(spot,{
        radius:10,
        color:'green',
        fillColor:'green',
        fillOpacity:0.8
      })
      .addTo(map)
      .bindPopup('Safe Spot');

    });

  });
  const dangerZones = [

    [currentLat + 0.015,currentLng - 0.02],
    [currentLat - 0.02,currentLng + 0.01]

  ];

  dangerZones.forEach((zone)=>{

      L.circle(zone,{

        radius:250,

        color:"#ef4444",

        fillColor:"#ef4444",

        fillOpacity:0.3

      })
      .addTo(map)
      .bindPopup("Unsafe Area");
  });

}

/* FIND ROUTE */

async function findRoute(){
    const destination = document.getElementById("destination").value;

    if(!destination){
        alert("Enter destination");
        return;
    }

    // NEW: Check if geolocation has finished loading
    if(!currentLat || !currentLng) {
        alert("Still getting your location. Please wait a moment and ensure location permissions are granted.");
        return;
    }

    try{
        // SEARCH LOCATION
        const response = await fetch(
            `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(destination)}`
        );

        const data = await response.json();

        if(data.length === 0){
            alert("Location not found. Try a different search term.");
            return;
        }

        const destLat = parseFloat(data[0].lat);
        const destLng = parseFloat(data[0].lon);

        // REMOVE OLD ROUTE
        if(routingControl){
            map.removeControl(routingControl);
        }

        // CREATE ROUTE
        routingControl = L.Routing.control({
            waypoints:[
                L.latLng(currentLat,currentLng),
                L.latLng(destLat,destLng)
            ],
            routeWhileDragging:true,
            draggableWaypoints:false,
            addWaypoints:false,
            fitSelectedRoutes:true,
            showAlternatives:true,
            lineOptions:{
                styles:[
                    {
                        color:"#10b981",
                        weight:8,
                        opacity:0.95
                    }
                ]
            },
            altLineOptions:{
                styles:[
                    {
                        color:"#94a3b8",
                        weight:6,
                        opacity:0.5
                    }
                ]
            }
        }).addTo(map);

        // DYNAMIC SAFETY SCORE
        generateLiveSafety();

    } catch(err) {
        console.error("Routing Error:", err);
        // NEW: Show the actual error message in the alert to help with debugging
        alert("Route failed: " + err.message);
    }
}
function generateLiveSafety(){

    const safety =
    Math.floor(Math.random() * 20) + 80;

    let level = "";

    if(safety > 90){

        level = "Very Safe";

    }else if(safety > 84){

        level = "Safe";

    }else{

        level = "Moderate Risk";
    }

    document.querySelector(
    ".statCard h3"
    ).innerHTML = `${safety}%`;

    document.querySelector(
    ".statCard span"
    ).innerHTML = level;
}
setInterval(()=>{

    generateLiveSafety();

},7000);

/* SHARE LOCATION */

function shareLocation(){

  const link =
    `https://maps.google.com/?q=${currentLat},${currentLng}`;

  if(navigator.share){

    navigator.share({
      title:'My Live Location',
      text:'Track my live location',
      url:link
    });

  }else{

    prompt('Copy this link',link);

  }

}

/* SOS */

function sendSOS() {
  navigator.vibrate([300,100,300]);
   navigator.geolocation.getCurrentPosition((position) => {

      const lat = position.coords.latitude;
      const lng = position.coords.longitude;

      const locationLink =
      `https://www.google.com/maps?q=${lat},${lng}`;

      const message =
      `Help me, I am not feeling safe right now.
Please contact me immediately and check my live location.

${locationLink}`;

      const contact =
      localStorage.getItem("rr_contact");

      const whatsappURL =
      `https://wa.me/91${contact}?text=${encodeURIComponent(message)}`;

      window.open(whatsappURL, "_blank");

   }, () => {

      alert("Unable to fetch live location");

   });

}
/* FOCUS LOCATION */

function focusLocation(){

  map.setView([currentLat,currentLng],15);

}

/* DRAGGABLE SHEET */

const sheet = document.getElementById('bottomSheet');
const handle = document.getElementById('dragHandle');

const SNAP_TOP = window.innerHeight * 0.92;
const SNAP_MID = window.innerHeight * 0.52;
const SNAP_MIN = 120;

let startY = 0;
let startHeight = 0;
let isDragging = false;

function dragStart(e){

  isDragging = true;

  startY = e.touches
    ? e.touches[0].clientY
    : e.clientY;

  startHeight = sheet.offsetHeight;

  sheet.style.transition = 'none';

  document.addEventListener('mousemove', dragging);
  document.addEventListener('touchmove', dragging);

  document.addEventListener('mouseup', dragEnd);
  document.addEventListener('touchend', dragEnd);

}

function dragging(e){

  if(!isDragging) return;

  const currentY = e.touches
    ? e.touches[0].clientY
    : e.clientY;

  const delta = startY - currentY;

  let newHeight = startHeight + delta;

  if(newHeight < SNAP_MIN)
    newHeight = SNAP_MIN;

  if(newHeight > SNAP_TOP)
    newHeight = SNAP_TOP;

  sheet.style.height = `${newHeight}px`;

}

function dragEnd(){

  isDragging = false;

  let current = sheet.offsetHeight;

  sheet.style.transition =
  'height .34s cubic-bezier(.22,1,.36,1)';

  if(current < 240){

    sheet.style.height = `${SNAP_MIN}px`;

  }
  else if(current < 540){

    sheet.style.height = `${SNAP_MID}px`;

  }
  else{

    sheet.style.height = `${SNAP_TOP}px`;

  }

  document.removeEventListener('mousemove', dragging);
  document.removeEventListener('touchmove', dragging);

}

handle.addEventListener('mousedown', dragStart);
handle.addEventListener('touchstart', dragStart);
