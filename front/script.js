const pacienteBtn = document.getElementById("pacienteBtn");
const medicoBtn = document.getElementById("medicoBtn");

const emailField = document.getElementById("emailField");
const crmField = document.getElementById("crmField");

const showLogin = document.getElementById("showLogin");
const showCadastroPaciente = document.getElementById("showCadastroPaciente");
const showCadastroMedico = document.getElementById("showCadastroMedico");

const loginForm = document.getElementById("loginFormSection");
const pacienteForm = document.getElementById("cadastroPacienteSection");
const medicoForm = document.getElementById("cadastroMedicoSection");

let currentUserType = "paciente";

pacienteBtn.addEventListener("click", () => {
  currentUserType = "paciente";
  emailField.classList.remove("hidden");
  crmField.classList.add("hidden");
  pacienteBtn.classList.replace("bg-gray-200", "bg-blue-500");
  pacienteBtn.classList.replace("text-gray-700", "text-white");
  medicoBtn.classList.replace("bg-blue-500", "bg-gray-200");
  medicoBtn.classList.replace("text-white", "text-gray-700");
});

medicoBtn.addEventListener("click", () => {
  currentUserType = "medico";
  crmField.classList.remove("hidden");
  emailField.classList.add("hidden");
  medicoBtn.classList.replace("bg-gray-200", "bg-blue-500");
  medicoBtn.classList.replace("text-gray-700", "text-white");
  pacienteBtn.classList.replace("bg-blue-500", "bg-gray-200");
  pacienteBtn.classList.replace("text-white", "text-gray-700");
});

document.getElementById("login-form").addEventListener("submit", function (e) {
  e.preventDefault();
  if (currentUserType === "paciente") {
    const email = document.getElementById("email").value;
    if (email && document.getElementById("password").value) {
      window.location.href = "areaPaciente.html";
    } else {
      alert("Preencha todos os campos.");
    }
  } else {
    const crm = document.getElementById("crm").value;
    if (crm && document.getElementById("password").value) {
      window.location.href = "areaMedico.html";
    } else {
      alert("Preencha todos os campos.");
    }
  }
});
function hideAll() {
  loginForm.classList.add("hidden");
  pacienteForm.classList.add("hidden");
  medicoForm.classList.add("hidden");
}

showLogin.addEventListener("click", () => {
  hideAll();
  loginForm.classList.remove("hidden");
  highlightActive(showLogin);
});

showCadastroPaciente.addEventListener("click", () => {
  hideAll();
  pacienteForm.classList.remove("hidden");
  highlightActive(showCadastroPaciente);
});

showCadastroMedico.addEventListener("click", () => {
  hideAll();
  medicoForm.classList.remove("hidden");
  highlightActive(showCadastroMedico);
});

function highlightActive(activeBtn) {
  document.querySelectorAll(".tab-btn").forEach((btn) => {
    btn.classList.remove("bg-blue-500", "text-white");
    btn.classList.add("bg-gray-200", "text-gray-700");
  });
  activeBtn.classList.remove("bg-gray-200", "text-gray-700");
  activeBtn.classList.add("bg-blue-500", "text-white");
}
