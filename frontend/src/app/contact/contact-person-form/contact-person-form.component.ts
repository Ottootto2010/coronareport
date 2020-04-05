import { DeactivatableComponent } from './../../guards/prevent-unsaved-changes.guard';
import { ContactPersonDto } from './../../models/contact-person';
import { Component, OnInit, Input, Output, EventEmitter, OnDestroy, HostListener } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { SubSink } from 'subsink';
import { ApiService } from 'src/app/services/api.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-contact-person-form',
  templateUrl: './contact-person-form.component.html',
  styleUrls: ['./contact-person-form.component.scss']
})
export class ContactPersonFormComponent implements OnInit, OnDestroy, DeactivatableComponent {
  @Input() contactPerson: ContactPersonDto;
  @Output() contactCreated = new EventEmitter<ContactPersonDto>();
  @Output() contactModified = new EventEmitter<any>();
  @Output() cancelled = new EventEmitter<any>();
  formGroup: FormGroup;
  private subs = new SubSink();


  constructor(
    private formBuilder: FormBuilder,
    private apiService: ApiService,
    private snackbarService: SnackbarService,

  ) { }

  ngOnInit() {
    this.buildForm();
  }

  ngOnDestroy(): void {
    this.subs.unsubscribe();
  }


  @HostListener('window:beforeunload')
  canDeactivate(): Observable<boolean> | boolean {
    return this.formGroup.pristine;
  }

  get isNew() {
    return (!this.contactPerson.id);
  }

  buildForm() {
    this.formGroup = this.formBuilder.group(
      {
        firstname: new FormControl(this.contactPerson.firstname, [Validators.required]),
        surename: new FormControl(this.contactPerson.surename, [Validators.required]),
        email: new FormControl(this.contactPerson.email, [Validators.email]),
        phone: new FormControl(this.contactPerson.phone, [Validators.required]),
      }
    );
  }

  onSubmit() {
    if (this.formGroup.valid) {
      Object.assign(this.contactPerson, this.formGroup.value);

      if (this.isNew) {
        this.createContactPerson();
      } else {
        this.modifyContactPerson();
      }
    }
  }

  createContactPerson() {
    this.subs.add(this.apiService
      .createContactPerson(this.contactPerson)
      .subscribe(createdContactPerson => {
        this.snackbarService.success('Kontaktperson erfolgreich angelegt');
        this.contactCreated.emit(createdContactPerson);
      }));
  }

  modifyContactPerson() {
    this.subs.add(this.apiService
      .modifyContactPerson(this.contactPerson)
      .subscribe(_ => {
        this.snackbarService.success('Kontaktperson erfolgreich aktualisiert');
        this.contactModified.emit();
      }));
  }

  cancel() {
    this.cancelled.emit();
  }
}
