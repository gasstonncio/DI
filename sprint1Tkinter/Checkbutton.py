import tkinter as tk


def show_selection():
    #Metodo para lanzar la eleccion del mensaje segun las casillas seleccionadas
    i=0
    estado=""

    #El siguiente if controla los valores booleanos de los botones seleccionados, segun los seleccionados, la suma da un resultado diferente
    if var_musica.get():
        i=i+2
    if var_juegos.get():
        i=i+4
    if var_cine.get():
        i=i+10

    #El siguiente if guarda un valor string en la variable estado según la suma de los ifs anteriores
    if i==16:
        estado="Todas las aficiones van contigo!"
    else:
        if i==0:
            estado="Parece que ninguna de las aficiones va contigo..."
        else:
            if i==2:
                estado="Tu afición es la música!"
            else:
                if i==4:
                    estado="Tu afición son los videjuegos!"
                else:
                    if i==10:
                        estado="Tu afición es el cine!"
                    else:
                        if i==6:
                            estado="Tus aficiones son la música y los videojuegos!"
                        else:
                            if i==12:
                                estado="Tus aficiones son la música y el cine!"
                            else:
                                if i==14:
                                    estado="Tus aficiones son los videjuegos y el cine!"

    #Ejecutamos los cambios al llamar al boton
    etiqueta.config(text=estado)

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