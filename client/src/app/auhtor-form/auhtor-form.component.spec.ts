import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuhtorFormComponent } from './auhtor-form.component';

describe('AuhtorFormComponent', () => {
  let component: AuhtorFormComponent;
  let fixture: ComponentFixture<AuhtorFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AuhtorFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AuhtorFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
