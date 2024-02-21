// 초기화
let employees = [];
const PROJECT_ROOT = "/4일_서블릿";
function addRow() {
    console.log("행 추가");
    
    let employeesTable = document.getElementById("employeeBook");
    let newRow = document.createElement("tr");
    let newRowId = "row" + employeesTable.rows.length;
    
    newRow.setAttribute("id", newRowId);

    for (let i = 0; i < 6; i++) {
        let newCell = document.createElement("td");
        let newInput = document.createElement("input");
        newInput.setAttribute("type", "text");
        newCell.appendChild(newInput);
        newRow.appendChild(newCell);
    }

    let deleteButtonCell = document.createElement("td");
    let deleteButton = document.createElement("button");
    deleteButton.setAttribute("class", "deleteRow");
    deleteButton.innerHTML = "삭제";
    deleteButton.addEventListener("click", function() {
        deleteRow(this); // 삭제 버튼을 클릭하면 해당 행을 삭제하는 함수 호출
    });
    deleteButtonCell.appendChild(deleteButton);
    newRow.appendChild(deleteButtonCell);

    employeesTable.appendChild(newRow);
}
function saveRow() {
    console.log("행 저장");
    
    let newRow = document.getElementById("employeeBook").rows[document.getElementById("employeeBook").rows.length - 1];
    
    let newEmployee = {
        department: newRow.cells[0].children[0].value,
        name: newRow.cells[1].children[0].value,
        position: newRow.cells[2].children[0].value,
        englishName: newRow.cells[3].children[0].value,
        phoneNumber: newRow.cells[4].children[0].value,
        email: newRow.cells[5].children[0].value,
        id: 'row' + (document.getElementById("employeeBook").rows.length - 1)
    };

    // 추가한 행 정보를 employees 배열에 추가
    employees.push(newEmployee);

    // 테이블에 새로운 행 데이터를 출력
    for (let i = 0; i < 6; i++) {
        newRow.cells[i].innerHTML = newEmployee[Object.keys(newEmployee)[i]];
    }
}

function deleteRow(clickedButton) {
    console.log("행 삭제");
    
    let row = clickedButton.closest("tr"); // 클릭된 버튼이 속한 <tr> 요소를 찾습니다.
    let rowIndex = row.rowIndex; // 클릭된 행의 인덱스를 가져옵니다.
    let rowId = "row" + (rowIndex - 1); // 행의 id를 생성합니다.

    // employees 배열에서 삭제할 행을 필터링합니다.
    employees = employees.filter(employee => employee.id !== rowId);

    // 테이블에서 행을 삭제합니다.
    row.remove();
}

function saveToXML() {
    console.log("XML로 저장");
    
    let tableRows = document.getElementById("employeeBook").getElementsByTagName("tr");
    
    // 테이블의 모든 행을 순회하면서 데이터를 추출하여 employees 배열에 추가
    let employees = [];
    let addressBookRows = document.querySelectorAll("#employeeBook tr");
    for (let i = 1; i < addressBookRows.length; i++) { // index 0은 테이블 헤더이므로 생략
        let currentRow = addressBookRows[i];
        let cells = currentRow.getElementsByTagName("td");
        let department = cells[0].textContent;
        let name = cells[1].textContent;
        let position = cells[2].textContent;
        let englishName = cells[3].textContent;
        let phoneNumber = cells[4].textContent;
        let email = cells[5].textContent;
        
        let employee = {
            department: department,
            name: name,
            position: position,
            englishName: englishName,
            phoneNumber: phoneNumber,
            email: email
        };
        employees.push(employee);
    }

    // XML로 데이터 전송
    sendXMLDataToServer(employees);
}

function sendXMLDataToServer(employees) {
    axios.post(`${PROJECT_ROOT}/savetoxml`, { employees: employees })
        .then(response => {
            const blob = new Blob([response.data]);
            let link = document.createElement('a');
            link.href = window.URL.createObjectURL(blob);
            link.download = "employees.xml";
            link.click();
        })
        .catch(error => {
            alert("다운로드 실패 - " + error.response.data.statusDescription);
        });
}

function saveToDB() {
    console.log("DB에 저장");
    
    let tableRows = document.getElementById("employeeBook").getElementsByTagName("tr");
    
    // 테이블의 모든 행을 순회하면서 데이터를 추출하여 employees 배열에 추가
   let employees = [];
    let addressBookRows = document.querySelectorAll("#employeeBook tr");
    for (let i = 1; i < addressBookRows.length; i++) { // index 0은 테이블 헤더이므로 생략
        let currentRow = addressBookRows[i];
        let cells = currentRow.getElementsByTagName("td");
        let department = cells[0].textContent;
        let name = cells[1].textContent;
        let position = cells[2].textContent;
        let englishName = cells[3].textContent;
        let phoneNumber = cells[4].textContent;
        let email = cells[5].textContent;
        
        let employee = {
            department: department,
            name: name,
            position: position,
            englishName: englishName,
            phoneNumber: phoneNumber,
            email: email
        };
        employees.push(employee);
    }


    // DB에 데이터 전송
    sendDBDataToServer(employees);
}

function sendDBDataToServer(employees) {
    axios.post(`${PROJECT_ROOT}/savetodb`, { employees: employees })
        .then(response => {
            if (response.data.status == "SUCCESS") {
                alert("DB에 저장 성공");
            } else {
                alert("DB에 저장 실패");
            }
        })
        .catch(error => {
            alert("DB에 저장 실패 - " + error.response.data.statusDescription);
      });
}


