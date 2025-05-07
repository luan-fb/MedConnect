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

  if (currentUserType === "paciente" && email && password) {
    await fazerLogin("/auth/paciente/login", { email, senha: password }, "areaPaciente.html");
  } else if (currentUserType === "medico" && crm && password) {
    await fazerLogin("/auth/medico/login", { crm, senha: password }, "areaMedico.html");
  } else {
    alert("Preencha todos os campos corretamente.");
  }
});

async function fazerLogin(endpoint, payload, redirectPage) {
  try {
    const response = await fetch(`http://localhost:8080${endpoint}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(payload),
    });

    if (!response.ok) {
      const erro = await response.text();
      throw new Error(erro || "Falha no login.");
    }

    const result = await response.json();
    localStorage.setItem("token", result.token);
    window.location.href = redirectPage;
  } catch (error) {
    console.error("Erro no login:", error);
    alert("Falha ao fazer login. Verifique suas credenciais.");
  }
}
