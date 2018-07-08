int i = 0b0011;
int j = 0B1010;

int add()
{
    int j, sum = 0;

    for ( j = 1; j <= LAST; j++ ) {
      sum += j;
    } /*-for-*/
    printf("sum = %d\n", sum);

    return 0;
}

struct tree_el {
   int val;
   struct tree_el * right, * left;
};

typedef struct tree_el node;

void insert(node ** tree, node * item) {
   if(!(*tree)) {
      *tree = item;
      return;
   }
   if(item->val<(*tree)->val)
      insert(&(*tree)->left, item);
   else if(item->val>(*tree)->val)
      insert(&(*tree)->right, item);
}

void printout(node * tree) {
   if(tree->left) printout(tree->left);
   printf("%d\n",tree->val);
   if(tree->right) printout(tree->right);
}

void tree() {
   node * curr, * root;
   int i;

   root = NULL;

   for(i=1;i<=10;i++) {
      curr = (node *)malloc(sizeof(node));
      curr->left = curr->right = NULL;
      curr->val = rand();
      insert(&root, curr);
   }

   printout(root);
}

main()
{
    /* Define default values: */

    int n = 0;
    float x = 0.0;

    /* Define contents of dialog window */

    create_int_dialog_entry("n", &n);
    create_float_dialog_entry("x", &x);

    /* Create window with name "Setup" and top-left corner at (0,0) */

    set_up_dialog("Setup", 0, 0);

    /* Display the window and read the results */

    read_dialog_window();

    /* Print out the new values */

    printf("n = %d, x = %f\n", n, x);
}