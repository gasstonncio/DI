import tkinter as tk

#Metodo para recorrer la lista y ver la seleccion
def show_selection():
    selection=listbox.curselection()
    elements=[listbox.get(i) for i in selection]
    etiqueta.config(text=f"Seleccionados: {', '.join(elements)}")

#Creamos la ventana
root=tk.Tk()
root.title("Ejercicio 6")
root.geometry("600x300")

#Creamos una lista con elementos
list=["Fresas","Mandarinas","Peras","Manzana","Platano"]

#Cramos el listbox
listbox=tk.Listbox(root, selectmode=tk.MULTIPLE)
for option in list:
    listbox.insert(tk.END,option)
listbox.pack(pady=25)

#Creamos el boton que nos mostrara las selecciones al pulsarlo
button=tk.Button(root, text="Mostrar seleccionado", command=show_selection)
button.pack(pady=10)

#Etiqueta donde se vera la seleccion
etiqueta=tk.Label(root, text="Ningun seleccionado")
etiqueta.pack(pady=10)

root.mainloop()