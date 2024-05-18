package model;

public class Category {
    private String name;

    // Constructor que acepta un String para el nombre
    public Category(String name) {
        this.name = name;
    }

    // Getter para el nombre
    public String getName() {
        return name;
    }

    // Setter para el nombre, en caso de que necesites cambiar el nombre después
    public void setName(String name) {
        this.name = name;
    }

    // Sobrescribir toString() para que el nombre de la categoría se muestre correctamente
    @Override
    public String toString() {
        return name;
    }

    // Sobrescribir equals() y hashCode() para asegurar que las categorías sean comparables
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Category category = (Category) obj;

        return name != null ? name.equalsIgnoreCase(category.name) : category.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.toLowerCase().hashCode() : 0;
    }
}
