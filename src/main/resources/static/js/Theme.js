console.log("theme");

function UpdateTheClass(prevTheme, newTheme) {
  document.documentElement.classList.remove(prevTheme);
  document.documentElement.classList.add(newTheme);

  document.getElementById("themeButton").getElementsByTagName("span")[0].textContent = newTheme=="light" ? "Light" : "Dark";
}

function SetTheme(newTheme) {
  var prevTheme = newTheme == "light" ? "dark" : "light";

  // Update localStorage without removing the previous item
  localStorage.setItem("themeClass", newTheme);

  // Update classes on the <html> element
  UpdateTheClass(prevTheme, newTheme);
}

function UpdateTheme() {
  console.log("button clicked");

  // Get the current class from localStorage, defaulting to "light"
  var className = localStorage.getItem("themeClass");
  var currentClass = className == null ? "light" : className;

  // Determine the new class
  var newClass = currentClass == "light" ? "dark" : "light";

  // Set the new theme
  SetTheme(newClass);
}

document.addEventListener("DOMContentLoaded", function () {
  var storedTheme = localStorage.getItem("themeClass") || "light"; // Default to "light"
  UpdateTheClass(storedTheme == "light" ? "dark" : "light", storedTheme);
  document.getElementById("themeButton").addEventListener("click", UpdateTheme);
});
