package br.edu.ifsp.scl.sdm.layoutssdm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collection;

import static br.edu.ifsp.scl.sdm.layoutssdm.R.layout.frame_layout_activity_main;
import static br.edu.ifsp.scl.sdm.layoutssdm.R.layout.linear_layout_activity_main;
import static br.edu.ifsp.scl.sdm.layoutssdm.R.layout.relative_layout_activity_main;
import static br.edu.ifsp.scl.sdm.layoutssdm.R.layout.scroll_view_activity_main;
import static br.edu.ifsp.scl.sdm.layoutssdm.R.layout.table_layout_activity_main;

public class MainActivity extends AppCompatActivity {
    private final String ESTADO_NOTIFICACAO_CHECKBOX = "ESTADO_NOTIFICACAO_CHECKBOX";
    private final String NOTIFICACAO_RADIOBUTTON_SELECTED = "NOTIFICACAO_RADIOBUTTON_SELECTED";
    private final String TELEFONE_ADICIONAL_ARRAY = "TELEFONE_ADICIONAL_ARRAY";
    private final String EMAIL_ADICIONAL_ARRAY = "EMAIL_ADICIONAL_ARRAY";

    private CheckBox notificacoesCheckBox;
    private RadioGroup notificacoesRadioGroup;
    private EditText nomeEditText;
    private EditText telefoneEditText;
    private EditText emailEditText;
    private LinearLayout telefoneLinearLayout;
    private LinearLayout emailLinearLayout;
    private ArrayList<View> telefoneArrayList;
    private ArrayList<View> emailArrayList;
    private LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_view_activity_main);

        // Referencia para view
        notificacoesCheckBox = findViewById(R.id.notificacoesCheckBox);
        notificacoesRadioGroup = findViewById(R.id.notificacoesRadioGoup);
        nomeEditText = findViewById(R.id.nomeEditText);
        telefoneEditText = findViewById(R.id.telefoneEditText);
        emailEditText = findViewById(R.id.emailEditText);
        telefoneLinearLayout = findViewById(R.id.telefoneLinearLayout);
        emailLinearLayout = findViewById(R.id.emailLinearLayout);
        telefoneArrayList = new ArrayList<>();
        emailArrayList = new ArrayList<>();
        layoutInflater = getLayoutInflater();

        notificacoesCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    notificacoesRadioGroup.setVisibility(View.VISIBLE);
                }
                else {
                    notificacoesRadioGroup.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ESTADO_NOTIFICACAO_CHECKBOX, notificacoesCheckBox.isChecked());
        outState.putInt(NOTIFICACAO_RADIOBUTTON_SELECTED,notificacoesRadioGroup.getCheckedRadioButtonId());
        outState.putSerializable(EMAIL_ADICIONAL_ARRAY, emailArrayList);
        outState.putSerializable(TELEFONE_ADICIONAL_ARRAY, telefoneArrayList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        notificacoesCheckBox.setChecked(savedInstanceState.getBoolean(ESTADO_NOTIFICACAO_CHECKBOX,false));

        int idRadioButtonSelecionado = savedInstanceState.getInt(NOTIFICACAO_RADIOBUTTON_SELECTED,-1);
        if (idRadioButtonSelecionado != -1) {
            notificacoesRadioGroup.check(idRadioButtonSelecionado);
        }

        emailArrayList.addAll((Collection<? extends View>) savedInstanceState.getSerializable(EMAIL_ADICIONAL_ARRAY));
        telefoneArrayList.addAll((Collection<? extends View>) savedInstanceState.getSerializable(TELEFONE_ADICIONAL_ARRAY));

        for (View view : emailArrayList) {
            EditText editText = view.findViewById(R.id.emailAuxEditText);
            String value = editText.getText().toString();

            Spinner spinner = view.findViewById(R.id.tipoEmailSpinner);
            Integer selectedPosition = spinner.getSelectedItemPosition();

            View restoredEmailView = layoutInflater.inflate(R.layout.novo_email_layout,null);
            EditText restoredEditText = restoredEmailView.findViewById(R.id.emailAuxEditText);
            Spinner restoredSpinner = restoredEmailView.findViewById(R.id.tipoEmailSpinner);

            restoredSpinner.setSelection(selectedPosition);
            restoredEditText.setText(value);

            emailLinearLayout.addView(restoredEmailView);
        }

        for (View view : telefoneArrayList) {
            EditText editText = view.findViewById(R.id.telefoneAuxEditText);
            String value = editText.getText().toString();

            Spinner spinner = view.findViewById(R.id.tipoTelefoneSpinner);
            Integer selectedPosition = spinner.getSelectedItemPosition();

            View restoredTelefoneView = layoutInflater.inflate(R.layout.novo_telefone_layout,null);
            EditText restoredEditText = restoredTelefoneView.findViewById(R.id.telefoneAuxEditText);
            Spinner restoredSpinner = restoredTelefoneView.findViewById(R.id.tipoTelefoneSpinner);

            restoredSpinner.setSelection(selectedPosition);
            restoredEditText.setText(value);

            telefoneLinearLayout.addView(restoredTelefoneView);
        }
    }

    public void adicionarTelefone(View view) {
        if (view.getId() == R.id.adicionarTelefoneButton) {
            View novoTelefoneLayout = layoutInflater.inflate(R.layout.novo_telefone_layout, null);
            telefoneArrayList.add(novoTelefoneLayout);
            telefoneLinearLayout.addView(novoTelefoneLayout);
        }
    }

    public void adicionarEmail(View view) {
        if (view.getId() == R.id.adicionarEmailButton) {
            View novoEmailLayout = layoutInflater.inflate(R.layout.novo_email_layout,null);
            emailArrayList.add(novoEmailLayout);
            emailLinearLayout.addView(novoEmailLayout);
        }
    }

    public void limparFormulario(View view) {
        nomeEditText.setText("");
        emailEditText.setText("");
        telefoneEditText.setText("");
        notificacoesRadioGroup.clearCheck();
        notificacoesCheckBox.setChecked(false);
        nomeEditText.requestFocus();
    }
}
