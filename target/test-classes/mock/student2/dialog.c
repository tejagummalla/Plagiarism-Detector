main()
{
    /* Define default values: */

    int n = 0;
    float x = 0.0;

    /* Define contents of dialog window */

    create_int_dialog_entry("n", &n);
    create_float_dialog_entry("x", &x);

    set_up_dialog("Setup", 0, 0);
    read_dialog_window();

    printf("n = %d, x = %f\n", n, x);
}

