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

// Envio do formulário
loginForm?.addEventListener("submit", async (e) => {
  e.preventDefault();

  const password = document.getElementById("password").value;
  const email = document.getElementById("email")?.value;
  const crm = document.getElementById("crm")?.value;

  try {
    const response = await fetch("source/usuarios.json");
    const usuarios = await response.json();

    let usuarioValido = null;

    if (currentUserType === "paciente") {
      usuarioValido = usuarios.find(
        (u) => u.tipo === "paciente" && u.email === email && u.senha === password
      );
    } else {
      usuarioValido = usuarios.find(
        (u) => u.tipo === "medico" && u.crm === crm && u.senha === password
      );
    }

    if (usuarioValido) {
      alert("Login realizado com sucesso!");
      const destino = currentUserType === "paciente" ? "areaPaciente.html" : "areaMedico.html";
      window.location.href = destino;
    } else {
      alert("Credenciais inválidas.");
    }
  } catch (error) {
    console.error("Erro ao carregar usuários:", error);
    alert("Erro ao validar login. Verifique o console.");
  }
});
