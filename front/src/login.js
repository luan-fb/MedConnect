const pacienteBtn = document.getElementById("pacienteBtn");
const medicoBtn = document.getElementById("medicoBtn");
const emailField = document.getElementById("emailField");
const crmField = document.getElementById("crmField");
const loginForm = document.getElementById("login-form");

let currentUserType = "paciente";

// Alterna tipo de login
pacienteBtn?.addEventListener("click", () => switchUserType("paciente"));
medicoBtn?.addEventListener("click", () => switchUserType("medico"));

function switchUserType(type) {
  currentUserType = type;
  const isPaciente = type === "paciente";

  emailField.classList.toggle("hidden", !isPaciente);
  crmField.classList.toggle("hidden", isPaciente);

  pacienteBtn.classList.toggle("bg-blue-500", isPaciente);
  pacienteBtn.classList.toggle("text-white", isPaciente);
  pacienteBtn.classList.toggle("bg-gray-200", !isPaciente);
  pacienteBtn.classList.toggle("text-gray-700", !isPaciente);

  medicoBtn.classList.toggle("bg-blue-500", !isPaciente);
  medicoBtn.classList.toggle("text-white", !isPaciente);
  medicoBtn.classList.toggle("bg-gray-200", isPaciente);
  medicoBtn.classList.toggle("text-gray-700", isPaciente);
}

// Lida com envio do formulário
loginForm?.addEventListener("submit", async (e) => {
  e.preventDefault();

  const password = document.getElementById("password").value;
  const email = document.getElementById("email")?.value;
  const crm = document.getElementById("crm")?.value;

  if (currentUserType === "paciente" && email && password) {
    // TODO: Fazer requisição real para login do paciente
    // const result = await loginPaciente(email, password);
    // localStorage.setItem("token", result.token);
    window.location.href = "areaPaciente.html";
  } else if (currentUserType === "medico" && crm && password) {
    // TODO: Fazer requisição real para login do médico
    // const result = await loginMedico(crm, password);
    // localStorage.setItem("token", result.token);
    window.location.href = "areaMedico.html";
  } else {
    alert("Preencha todos os campos corretamente.");
  }
});
