// Add this to your Theme.js or a separate JS file
document.addEventListener("DOMContentLoaded", () => {
  const sidebar = document.getElementById("sidebar");
  const sidebarToggle = document.querySelector(
    '[data-drawer-toggle="sidebar"]'
  ); // Assuming you have a toggle button with this attribute

  if (sidebar && sidebarToggle) {
    sidebarToggle.addEventListener("click", () => {
      sidebar.classList.toggle("-translate-x-full");
    });

    // Optional: Close sidebar when clicking outside on small screens
    document.addEventListener("click", (event) => {
      if (
        window.innerWidth < 768 &&
        !sidebar.contains(event.target) &&
        !sidebarToggle.contains(event.target) &&
        !sidebar.classList.contains("-translate-x-full")
      ) {
        sidebar.classList.add("-translate-x-full");
      }
    });
  }
});
