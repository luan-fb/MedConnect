document.addEventListener("DOMContentLoaded", async () => {
    const token = localStorage.getItem("token");
  
    if (!token) {
      alert("Você precisa estar logado.");
      window.location.href = "login.html";
      return;
    }
  
    try {
      const response = await fetch("http://localhost:8080/api/consultas/paciente/futuras", {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
  
      if (!response.ok) {
        throw new Error("Erro ao buscar suas consultas.");
      }
  
      const consultas = await response.json();
      renderizarConsultas(consultas);
    } catch (error) {
      console.error(error);
      alert("Erro ao carregar suas consultas. Tente novamente mais tarde.");
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
  