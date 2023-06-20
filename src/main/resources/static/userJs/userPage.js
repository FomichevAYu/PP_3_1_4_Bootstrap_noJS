 const userurl = 'http://localhost:8080/api/user';

const authUser = fetch(userurl).then(response => response.json())
 authUser.then(user => {
         let roles = ''
         user.roles.forEach(role => {
             roles += ' '
             roles += role.name
         })
         document.getElementById("navbar-email").innerHTML = user.username
         document.getElementById("navbar-roles").innerHTML = roles
     }
 )

async function getUserPage() {
    let page = await fetch(userurl);

    if(page.ok) {
        let user = await page.json();
        getInformationAboutUser(user);
    } else {
        alert(`Error, ${page.status}`)
    }
}
function  getInformationAboutUser(user) {
    const tableBody = document.getElementById('userTableBody');
    let dataHtml = '';
    let roles = [];
    console.log('userSata', JSON.stringify(user))
    for (let role of user.roles) {
        roles.push(" " + role.name.toString()
            .replaceAll("ROLE_", ""))
    }
    dataHtml =
`<tr>
    <td>${user.id}</td>
    <td>${user.firstName}</td>
    <td>${user.lastName}</td>
    <td>${user.age}</td>
    <td>${user.username}</td>
    <td>${roles}</td>   
</tr>`

    tableBody.innerHTML = dataHtml;
}
getUserPage();