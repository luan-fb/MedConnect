document.addEventListener("DOMContentLoaded", async () => {
  // Parte real com API - comentada para uso futuro
  /*
  const token = localStorage.getItem("token");

  if (!token) {
    alert("Você precisa estar logado para acessar esta área.");
    window.location.href = "login.html";
    return;
  }

  try {
    const response = await fetch(
      "http://localhost:8080/api/consultas/paciente/futuras",
      {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    if (!response.ok) {
      throw new Error("Erro ao buscar suas consultas.");
    }

    const consultas = await response.json();
    renderizarConsultas(consultas);
  } catch (error) {
    console.error(error);
    alert("Erro ao carregar suas consultas. Tente novamente mais tarde.");
  }
  */

  // 🔁 Parte simulada (sem back-end)
  try {
    const response = await fetch("source/usuarios.json");
    const usuarios = await response.json();

    // Simula que o paciente logado é o com email "teste@paciente.com"
    const paciente = usuarios.find(
      (u) => u.tipo === "paciente" && u.email === "teste@paciente.com"
    );

    if (!paciente) {
      alert("Você precisa estar logado como paciente para acessar esta área.");
      window.location.href = "login.html";
      return;
    }

    const consultasMock = [
      {
        medico: "Dr. João Silva",
        dataHora: "2025-06-05T14:00:00",
      },
      {
        medico: "Dra. Fernanda Lima",
        dataHora: "2025-06-07T09:30:00",
      },
    ];

    renderizarConsultas(consultasMock);
  } catch (err) {
    console.error("Erro ao carregar dados simulados:", err);
    alert("Erro ao carregar informações locais.");
  }
});

function renderizarConsultas(consultas) {
  const container = document.getElementById("lista-consultas");
  container.innerHTML = "";

  if (!consultas.length) {
    container.innerHTML = `<div class="px-6 py-4 text-sm text-gray-500">Você não tem consultas futuras agendadas.</div>`;
    return;
  }

  consultas.forEach((consulta) => {
    const dataHora = new Date(consulta.dataHora);
    const horaFormatada = dataHora.toLocaleTimeString("pt-BR", {
      hour: "2-digit",
      minute: "2-digit",
    });

    const item = document.createElement("div");
    item.className = "px-6 py-4 flex items-center hover:bg-gray-50";
    item.innerHTML = `
      <div class="h-10 w-10 rounded-full bg-blue-100 flex items-center justify-center text-blue-500">
        <i class="fas fa-user-md"></i>
      </div>
      <div class="ml-4 flex-1">
        <div class="flex items-center justify-between">
          <p class="text-sm font-medium text-gray-800">${consulta.medico}</p>
          <p class="text-xs text-gray-500">${horaFormatada}</p>
        </div>
        <p class="text-xs text-gray-500 mt-1">Consulta agendada</p>
      </div>
    `;
    container.appendChild(item);
  });
}
