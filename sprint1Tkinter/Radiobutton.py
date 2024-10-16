import tkinter as tk


def selection():
    #Metodo que recoge el valor string del boton seleccionado para mandarle el color al root.configure
    seleccion = var_radio.get()
    if seleccion == "Gris":
        bcolor="grey"
    elif seleccion== "Azul":
        bcolor = "blue"
    elif seleccion== "Verde":
        bcolor="green"
    else:
        bcolor=""

    #Se ejecuta cuando elegimos una opcion lanzando un mensaje personalizado
    etiqueta.config(text=f"Color de ventana actualizado a {seleccion}",bg=f"{bcolor}")
    #Cuando se elige una opcion se cambia el color de la ventana "root" al seleccionado
    root.configure(bg=f"{bcolor}")

#Ventana principal
root=tk.Tk()
root.title("Ejercicio 5")
root.geometry("600x300")
root.configure(bg="grey")#Como se le da un valor inicial gris en la linea 29, le ponemos el color background acorde

#Se le da valor String a la variable para que guarde el color
var_radio=tk.StringVar()
var_radio.set("Gris") #Se le da un valor por defecto

gris=tk.Radiobutton(root, text="Gris", variable=var_radio,value="Gris" ,command=selection, bg="grey")
gris.pack(pady=25)
azul=tk.Radiobutton(root, text="Azul", variable=var_radio,value="Azul" ,command=selection, bg="blue")
azul.pack(pady=25)
verde=tk.Radiobutton(root, text="Verde", variable=var_radio,value="Verde" ,command=selection, bg="green")
verde.pack(pady=25)

#Etiqueta
etiqueta=tk.Label(root, text="El color de ventana por defecto es gris", bg="grey")
etiqueta.pack()

root.mainloop()