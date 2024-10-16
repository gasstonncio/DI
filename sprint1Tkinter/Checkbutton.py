import tkinter as tk


def show_selection():
    #Metodo para lanzar la eleccion del mensaje segun las casillas seleccionadas
    i=0
    #estado=""
    estado_array=["Parece que ninguna de las aficiones va contigo...","Tu afición es la música!","Tu afición son los videjuegos!",
                  "Tus aficiones son la música y los videojuegos!","Tu afición es el cine!","Tus aficiones son la música y el cine!",
                  "Tus aficiones son los videjuegos y el cine!", "Todas las aficiones van contigo!"]

    #El siguiente if controla los valores booleanos de los botones seleccionados, segun los seleccionados, la suma da un resultado diferente
    if var_musica.get():
        i=i+1
    if var_juegos.get():
        i=i+2
    if var_cine.get():
        i=i+4

    # #El siguiente if guarda un valor string en la variable estado según la suma de los ifs anteriores
    # Podríamos descomentar esta clase y comentar la lista, se deja las dos opciones pero se elije la mas eficiente
    # if i==7:
    #     estado="Todas las aficiones van contigo!"
    # else:
    #     if i==0:
    #         estado="Parece que ninguna de las aficiones va contigo..."
    #     else:
    #         if i==1:
    #             estado="Tu afición es la música!"
    #         else:
    #             if i==2:
    #                 estado="Tu afición son los videjuegos!"
    #             else:
    #                 if i==4:
    #                     estado="Tu afición es el cine!"
    #                 else:
    #                     if i==3:
    #                         estado="Tus aficiones son la música y los videojuegos!"
    #                     else:
    #                         if i==5:
    #                             estado="Tus aficiones son la música y el cine!"
    #                         else:
    #                             if i==6:
    #                                 estado="Tus aficiones son los videjuegos y el cine!"

    #Ejecutamos los cambios al llamar al boton
    #etiqueta.config(text=estado)
    etiqueta.config(text=estado_array[i])

#Ventana principal

root=tk.Tk()
root.title("Ejercicio 4")
root.geometry("600x300")

var_musica=tk.IntVar()
var_juegos=tk.IntVar()
var_cine=tk.IntVar()

#Checkbuttons

musica=tk.Checkbutton(root, text="Música", variable=var_musica, command=show_selection)
musica.pack(pady=25)

juegos=tk.Checkbutton(root, text="Videojuegos", variable=var_juegos, command=show_selection)
juegos.pack(pady=25)

cine=tk.Checkbutton(root, text="Cine", variable=var_cine, command=show_selection)
cine.pack(pady=25)

#Etiqueta

etiqueta=tk.Label(root, text="")
etiqueta.pack()

root.mainloop()