// =====================================================
// AI DISASTER ASSISTANT
// JavaScript Logic
// =====================================================


// ================= GLOBAL VARIABLES =================

let selectedDisaster = "flood";

let severity = 6;

let risk = 0;


// ================= DISASTER DATA =================

const disasterData = {

    flood: {

        name: "Flood",

        icon: "🌊",

        baseRisk: 18,

        warnings: [

            "HIGH WATER RISK: Low-lying areas may become unsafe.",

            "ROAD HAZARD: Avoid flooded roads, bridges and underpasses.",

            "SUPPLY ALERT: Keep drinking water, medicines and food ready."

        ],

        recommendations: [

            "Move to higher ground.",

            "Keep important documents and medicines safe.",

            "Never walk or drive through moving flood water."

        ]

    },


    earthquake: {

        name: "Earthquake",

        icon: "🏚️",

        baseRisk: 28,

        warnings: [

            "STRUCTURAL RISK: Damaged buildings may collapse.",

            "AFTERSHOCK ALERT: Be prepared for additional shaking.",

            "UTILITY HAZARD: Stay away from damaged electrical lines."

        ],

        recommendations: [

            "Drop, Cover and Hold On during shaking.",

            "Stay away from damaged buildings.",

            "Use stairs instead of elevators during evacuation."

        ]

    },


    fire: {

        name: "Fire",

        icon: "🔥",

        baseRisk: 35,

        warnings: [

            "FIRE PERIMETER: Leave high-risk areas immediately.",

            "SMOKE HAZARD: Smoke may travel beyond the visible fire.",

            "EVACUATION ALERT: Keep emergency roads clear."

        ],

        recommendations: [

            "Move away from fire and smoke.",

            "Stay low when smoke is present.",

            "Never re-enter a burning building."

        ]

    },


    storm: {

        name: "Extreme Weather",

        icon: "⛈️",

        baseRisk: 24,

        warnings: [

            "SEVERE WEATHER: Conditions may intensify quickly.",

            "WIND/LIGHTNING: Avoid exposed locations.",

            "TRAVEL RISK: Avoid unnecessary travel."

        ],

        recommendations: [

            "Stay indoors away from windows.",

            "Keep your phone charged.",

            "Avoid trees and exposed electrical equipment."

        ]

    }

};


// ================= SELECT DISASTER =================

function selectDisaster(type, button) {

    selectedDisaster = type;


    // Remove active class

    const buttons =
        document.querySelectorAll(".disaster");


    buttons.forEach(btn => {

        btn.classList.remove("active");

    });


    // Add active class

    button.classList.add("active");


    showToast(

        disasterData[type].icon
        + " "
        + disasterData[type].name
        + " selected."

    );

}


// ================= CHANGE SEVERITY =================

function changeSeverity() {

    severity =
        Number(
            document.getElementById("severity").value
        );


    document.getElementById(
        "severityValue"
    ).innerText =
        severity + " / 10";

}


// ================= CALCULATE RISK =================

function calculateRisk() {

    const disaster =
        disasterData[selectedDisaster];


    /*
        DEMO RISK ENGINE

        Risk =
        Base Disaster Risk
        +
        Severity Impact
    */


    risk =
        Math.round(
            disaster.baseRisk
            +
            severity * 6.2
        );


    // Limit between 5 and 100

    risk =
        Math.max(
            5,
            Math.min(
                100,
                risk
            )
        );


    return risk;

}


// ================= RISK LEVEL =================

function getRiskLevel(score) {

    if (score >= 80) {

        return "CRITICAL";

    }

    if (score >= 60) {

        return "HIGH";

    }

    if (score >= 35) {

        return "MEDIUM";

    }

    return "LOW";

}


// ================= ANALYZE =================

function analyzeRisk() {

    calculateRisk();


    const level =
        getRiskLevel(risk);


    // Update score

    document.getElementById(
        "riskScore"
    ).innerText =
        risk + "/100";


    // Update text

    document.getElementById(
        "riskText"
    ).innerText =
        level + " risk";


    // Risk zones

    document.getElementById(
        "riskZones"
    ).innerText =
        Math.max(
            1,
            Math.round(
                risk / 12
            )
        );


    // Alerts

    document.getElementById(
        "alerts"
    ).innerText =
        risk >= 35
            ? "3"
            : "1";


    // Response

    let response;


    if (risk >= 80) {

        response = "CRITICAL";

    }

    else if (risk >= 60) {

        response = "HIGH";

    }

    else if (risk >= 35) {

        response = "ELEVATED";

    }

    else {

        response = "NORMAL";

    }


    document.getElementById(
        "response"
    ).innerText =
        response;


    // Risk badge

    document.getElementById(
        "riskBadge"
    ).innerText =
        level;


    // Risk meter

    document.getElementById(
        "meterFill"
    ).style.width =
        risk + "%";


    // Assessment

    const disaster =
        disasterData[selectedDisaster];


    document.getElementById(
        "assessment"
    ).innerHTML =

        "<b>AI Assessment:</b><br><br>" +

        "Disaster Type: <b>"
        + disaster.name
        + "</b><br>" +

        "Severity: <b>"
        + severity
        + "/10</b><br>" +

        "Risk Score: <b>"
        + risk
        + "/100</b><br>" +

        "Risk Level: <b>"
        + level
        + "</b><br><br>" +

        "The system has analyzed the selected "
        + "disaster scenario and generated "
        + "risk-based recommendations.";


    // Recommendations

    showRecommendations(
        disaster.recommendations
    );


    // Alerts

    showAlerts(
        disaster.warnings
    );


    // Update map

    updateMap();


    showToast(

        "🧠 AI analysis completed! "
        + "Risk: "
        + risk
        + "/100"

    );

}


// ================= RECOMMENDATIONS =================

function showRecommendations(
    recommendations
) {

    const container =
        document.getElementById(
            "recommendations"
        );


    container.innerHTML = "";


    recommendations.forEach(
        (item, index) => {

            const div =
                document.createElement(
                    "div"
                );


            div.className =
                "recommendation";


            div.innerHTML =

                "<b>"
                + (index + 1)
                + ".</b> "
                + item;


            container.appendChild(
                div
            );

        }
    );

}


// ================= ALERTS =================

function showAlerts(
    warnings
) {

    const container =
        document.getElementById(
            "alertList"
        );


    container.innerHTML = "";


    warnings.forEach(
        warning => {

            const div =
                document.createElement(
                    "div"
                );


            div.className =
                "alert";


            div.innerHTML =

                "<div class='alert-icon'>⚠️</div>" +

                "<div>" +

                "<strong>"
                + warning.split(":")[0]
                + "</strong>" +

                "<p>"
                + warning
                + "</p>" +

                "</div>";


            container.appendChild(
                div
            );

        }
    );

}


// ================= UPDATE MAP =================

function updateMap() {

    const zones =
        document.querySelectorAll(
            ".risk-zone"
        );


    const high =
        document.querySelector(
            ".high-zone"
        );


    const medium =
        document.querySelector(
            ".medium-zone"
        );


    const low =
        document.querySelector(
            ".low-zone"
        );


    /*
        Change map based on risk
    */


    if (risk >= 80) {

        high.style.width = "160px";

        high.style.height = "160px";


        medium.style.width = "250px";

        medium.style.height = "250px";


        low.style.width = "320px";

        low.style.height = "320px";

    }

    else if (risk >= 60) {

        high.style.width = "130px";

        high.style.height = "130px";


        medium.style.width = "220px";

        medium.style.height = "220px";


        low.style.width = "290px";

        low.style.height = "290px";

    }

    else {

        high.style.width = "90px";

        high.style.height = "90px";


        medium.style.width = "180px";

        medium.style.height = "180px";


        low.style.width = "250px";

        low.style.height = "250px";

    }

}


// ================= SOS =================

function sos() {

    document.getElementById(
        "responseMessage"
    ).innerHTML =

        "<b>🆘 SOS ACTIVATED</b><br>" +

        "Emergency request simulated. " +

        "In the production version, "
        + "this can notify emergency "
        + "contacts and responders.";


    showToast(
        "🆘 SOS demo activated!"
    );

}


// ================= SHELTER =================

function shelter() {

    document.getElementById(
        "responseMessage"
    ).innerHTML =

        "<b>🏠 SAFE SHELTER FOUND</b><br>" +

        "Demo Safe Shelter: "
        + "Community Safe Center<br>" +

        "Distance: 1.8 km<br><br>" +

        "Production version can use "
        + "verified shelter data from "
        + "government/disaster APIs.";


    showToast(
        "🏠 Safe shelter found!"
    );

}


// ================= SAFE ROUTE =================

function safeRoute() {

    document.getElementById(
        "responseMessage"
    ).innerHTML =

        "<b>🛣️ SAFE ROUTE GENERATED</b><br>" +

        "A demo evacuation route has "
        + "been generated.<br><br>" +

        "Production version will use "
        + "real maps and automatically "
        + "avoid high-risk zones.";


    showToast(
        "🛣️ Safe route generated!"
    );

}


// ================= CONTACTS =================

function contacts() {

    document.getElementById(
        "responseMessage"
    ).innerHTML =

        "<b>📞 Emergency Contacts</b><br><br>" +

        "India Emergency: <b>112</b><br>" +

        "Fire: <b>101</b><br>" +

        "Ambulance: <b>108</b><br><br>" +

        "Local disaster-management "
        + "contacts can be added "
        + "in the final version.";


    showToast(
        "📞 Emergency contacts opened!"
    );

}


// ================= TOAST =================

function showToast(message) {

    const toast =
        document.getElementById(
            "toast"
        );


    toast.innerText =
        message;


    toast.classList.add(
        "show"
    );


    setTimeout(
        () => {

            toast.classList.remove(
                "show"
            );

        },
        3000
    );

}


// ================= INITIAL =================

// Show default analysis after page load

setTimeout(
    () => {

        analyzeRisk();

    },
    500
);