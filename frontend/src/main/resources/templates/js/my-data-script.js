// Create health-data dialog menu
const createHealthDataBtn = document.getElementById("create-health-data-btn");
const createHealthDataDialog = document.getElementById("create-health-data-dialog");
const createHealthDataDialogContainer = document.getElementById("create-health-data-dialog-container");
const cancelCreateHealthDataBtn = document.getElementById("cancel-create-health-data-btn");
const errorCreateHealthData = document.getElementById('error-create-health-data');
const deleteHealthDataButtons = document.getElementsByClassName("delete-health-data-btn");
const errorDeleteHealthData = document.getElementById('error-delete-health-data');

createHealthDataBtn.addEventListener("click", () =>
    createHealthDataDialog.showModal()
);
cancelCreateHealthDataBtn.addEventListener("click", () => {
        createHealthDataDialog.close();
        errorCreateHealthData.innerHTML = ``;
});

createHealthDataDialog.addEventListener('click', (event) => {
    if (event.target === createHealthDataDialog) {
        createHealthDataDialog.close();
    }
});


function deleteHealthData (id) {
    $.ajax({
        url: 'http://localhost:8080/users/health-data/' + id + '/delete',
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            "Authorization": `Bearer ${getToken()}`
        },
        success: function (data) {
            console.log("Delete health-data: id=" + id);
            location.reload();
        },
        error: function (error) {
            console.log("Error: delete health-data - status - " + error.status);
            errorDeleteHealthData.innerHTML = `
                    <h2>Error: status - ${error.status}</h2>
                `;
            errorCreateHealthData.style.display = "block";
        }
    });
}
